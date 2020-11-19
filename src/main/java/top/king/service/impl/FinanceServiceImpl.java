package top.king.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.king.common.BaseQuery;
import top.king.common.BaseService;
import top.king.common.ResultModel;
import top.king.entity.*;
import top.king.mapper.*;
import top.king.service.FinanceService;
import utils.Http;
import utils.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class FinanceServiceImpl extends BaseService implements FinanceService {
    @Autowired
    FundInfoMapper fundInfoMapper;
    @Autowired
    NetValueMapper netValueMapper;
    @Autowired
    FareRatioMapper fareRatioMapper;
    @Autowired
    ShareDetailMapper shareDetailMapper;
    @Autowired
    StaticShareMapper staticShareMapper;
    @Autowired
    DayMapper dayMapper;

    @Override
    public ResultModel<List> selectFundDict() {
        ResultModel<List> resultModel = new ResultModel<>();
        try {
            List fundDict = fundInfoMapper.selectFundDict();
            resultModel.setData(fundDict);
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("查询基金字典出错！", e);
            resultModel.setMsg("查询基金字典出错！");
            resultModel.setResult(false);
            return resultModel;
        }
    }

    @Override
    public ResultModel deleteFundInfos(List<String> primaryKey) {
        ResultModel resultModel = new ResultModel();
        try {
            fundInfoMapper.deleteFunds(primaryKey);
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("删除基金出错！", e);
            resultModel.setMsg("删除基金出错！");
            resultModel.setResult(false);
            return resultModel;
        }
    }

    @Override
    public ResultModel<List<FundInfo>> selectFundInfo(BaseQuery<FundInfo> param) {
        ResultModel<List<FundInfo>> resultModel = new ResultModel<>();
        try {
            PageHelper.startPage(param.getPageNum(), param.getPageSize());
            Page<FundInfo> fundInfos = (Page<FundInfo>) fundInfoMapper.selectFundInfo(param);
            resultModel.setData(fundInfos);
            resultModel.setCount(fundInfos.getTotal());
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("查询基金信息出错！", e);
            resultModel.setMsg("查询基金信息出错！");
            resultModel.setResult(false);
            return resultModel;
        }
    }

    @Override
    public ResultModel addFund(String fundcode) {
        FundInfo fundInfo = new FundInfo();
        fundInfo.setFundcode(fundcode);
        try {
            fundInfoMapper.deleteFundByFundCode(fundInfo);
            // 基金基本信息
            handleFundBriefly(fundInfo);
            // 插入基金信息表
            fundInfoMapper.insert(fundInfo);
            // 历史净值获取
            NetValue netValue = new NetValue();
            netValue.setFundcode(fundcode);
            netValueMapper.deleteNetValues(netValue);
            List<NetValue> netValues = handleHistoryNetValue(fundcode, 1, 3650);
            // 历史净值插入表中
            netValueMapper.batchInsert(netValues);
            return new ResultModel(true);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel(false);
        }
        // http://api.fund.eastmoney.com/f10/lsjz?fundCode=008086&pageIndex=1&pageSize=2000
        // Host:api.fund.eastmoney.com
        // Referer:http://fundf10.eastmoney.com/
        // 天天基金爬取基金历史净值，用来做走势图
        // 当天实时： http://fundgz.1234567.com.cn/js/008086.js
        // 资产配置
        // 股票占比
        // 债券占比
        //http://www.cninfo.com.cn
        //实时指数：http://74.push2.eastmoney.com/api/qt/clist/get?pn=1&pz=50&po=1&np=1&fltt=2&invt=2&fs=b:MK0010
    }

    public static Integer getNextWorkDay(Integer cur) {
        try {
            if (cur.toString().length() != 8) {
                return null;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate localDate = LocalDate.parse(cur.toString(), formatter);
            LocalDate days = localDate.plusDays(1);
            while (days.getDayOfWeek().getValue() == 6 || days.getDayOfWeek().getValue() == 7) {
                days = days.plusDays(1);
            }
            return Integer.parseInt(days.format(formatter));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 申购处理逻辑，入参：申购金额+申购日期+基金代码
     * 1.根据申购日期获得当日净值，如果净值不存在则type置9 暂存份额明细表，等待净值更新
     * 2.获得净值后查询确认日，为明细设置确认日、当日净值和本金
     * 3.根据申购金额计算申购费用：申购金额*费率/(1+费率)
     * 4.计算申购份额：(申购金额-申购费用)/当日单位净值
     * 5.使用赎回份额字段保留申购份额，用于之后赎回判断
     * 6.插入份额明细表
     * 7.更新静态份额表，暂存则不更新，计算持仓成本: 申购份额/(申购份额+原有份额)*当前净值+原有份额/(申购份额+原有份额)*原持仓成本
     * 持仓金额：原有金额+当前净值*当前份额
     *
     * @param shareDetail
     * @return
     */
    @Override
    public ResultModel purchaseHandle(ShareDetail shareDetail) {
        // 申购金额+申购日期+基金代码
        ResultModel resultModel = new ResultModel();
        try {
            // 申购日期净值获得，不存在净值则暂存份额明细表，等待净值更新后重新计算
            NetValue netValue = new NetValue();
            Integer realDate = dayMapper.selectRealDate(shareDetail.getDRequest());
            netValue.setCDate(realDate);
            netValue.setFundcode(shareDetail.getFundcode());
            List<NetValue> netValues = netValueMapper.selectByNetvalue(netValue);
            BigDecimal value = null;
            if (netValues.size() < 1) {
                shareDetail.setBusinessType("9");
            } else {
                value = netValues.get(0).getNetvalue();
                shareDetail.setBusinessType("0");
            }
            if (value != null) {
                // 确认日获取
                Integer nextWorkDay = dayMapper.selectNextWorkDay(realDate);
                shareDetail.setDDate(nextWorkDay);
                shareDetail.setNetvalue(value.doubleValue());
                shareDetail.setOriginal(shareDetail.getPBalance());
                // 根据申购金额获得申购费率，不存在返回FALSE
                Map<String, String> map = new HashMap<>(4);
                map.put("balance", String.valueOf(shareDetail.getPBalance()));
                map.put("fundcode", shareDetail.getFundcode());
                FareRatio fareRatio = fareRatioMapper.selectFareRatioByPM(map);
                if (fareRatio == null) {
                    resultModel.setResult(false);
                    resultModel.setMsg("请维护基金[" + map.get("fundcode") + "]的费率信息！");
                    return resultModel;
                }
                // 计算申购费用
                BigDecimal ratio = new BigDecimal(String.valueOf(fareRatio.getRatio()));
                BigDecimal balance = new BigDecimal(String.valueOf(shareDetail.getPBalance()));
                BigDecimal fare = balance.divide((ratio.add(new BigDecimal(1))), 4, BigDecimal.ROUND_HALF_UP).multiply(ratio).setScale(2, BigDecimal.ROUND_HALF_UP);
                shareDetail.setPurchase(fare.doubleValue());
                // 计算申购份额
                BigDecimal share = balance.subtract(fare).divide(value, 2, BigDecimal.ROUND_HALF_UP);
                shareDetail.setShares(share.doubleValue());
                // 保留申购份额，用于赎回扣减
                shareDetail.setRFare(share.doubleValue());
            }
            // 插入份额明细表
            shareDetailMapper.insertShareDetailByDuplicate(shareDetail);
            // 更细静态份额表
            if (shareDetail.getBusinessType().equals("9")) {
                // 申购暂存数据重新计算
                resultModel.setMsg("申购信息暂存，等待净值更新！");
            } else {
                StaticShare staticShare = new StaticShare();
                staticShare.setFundcode(shareDetail.getFundcode());
                staticShare.setShares(shareDetail.getShares());
                staticShare.setNetvalue(shareDetail.getNetvalue());
                BigDecimal bigDecimal = new BigDecimal(String.valueOf(shareDetail.getPBalance()));
                BigDecimal bigDecimal1 = new BigDecimal(String.valueOf(shareDetail.getPurchase()));
                staticShare.setBalances(bigDecimal.subtract(bigDecimal1).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                staticShareMapper.insertStaticShareByDuplicate(staticShare);
            }
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("基金申购出错！", e);
            resultModel.setMsg("基金申购出错！");
            resultModel.setResult(false);
            return resultModel;
        }
    }

    /**
     * 赎回处理逻辑，入参：赎回份额+申请日期+基金代码
     * 1.查询业务类型为0的申购确认信息(7为已全部赎回)，不能存在则返回FALSE并提示
     * 2.获得申请日净值，不存在则将业务类型设置为8，暂存份额明细表等待净值更新
     * 3.计算持有天数计算：赎回确认日-申购确认日  赎回费用：赎回份额*赎回费率*单位净值
     * 4.计算赎回金额，如果为申购明细的最后一笔赎回则赎回金额需减去申购费用
     * 5.插入静态份额表，重新计算持仓成本和持仓金额
     * 持仓成本：(原成本*原份额-当前净值*当前份额)/(原份额-当前份额)
     * 持仓金额：当前成本*剩余份额
     *
     * @param shareDetail
     * @return
     */
    @Override
    public ResultModel redeemHandle(ShareDetail shareDetail) {
        ResultModel resultModel = new ResultModel();
        // 赎回份额+申请日期+基金代码
        try {
            // 查询申购确认信息
            ShareDetail shareDetail1 = new ShareDetail();
            shareDetail1.setBusinessType("0");
            shareDetail1.setFundcode(shareDetail.getFundcode());
            List<ShareDetail> details = shareDetailMapper.selectShareDetailByType(shareDetail1);
            if (details.size() < 1) {
                resultModel.setResult(false);
                resultModel.setMsg("赎回操作不存在剩余份额！");
                return resultModel;
            }
            // 获得申请日期当日净值，不存在则暂存
            Integer realDate = dayMapper.selectRealDate(shareDetail.getDRequest());
            NetValue netValue = new NetValue();
            netValue.setCDate(realDate);
            netValue.setFundcode(shareDetail.getFundcode());
            List<NetValue> netValues = netValueMapper.selectByNetvalue(netValue);
            BigDecimal value = null;
            if (netValues.size() < 1) {
                shareDetail.setBusinessType("8");
            } else {
                value = netValues.get(0).getNetvalue();
                shareDetail.setBusinessType("1");
            }
            // 计算赎回费+赎回金额，根据赎回份额查询份额明细表，先进先出计算持有天数
            List<StaticShare> staticShares = new ArrayList<>();
            if (value != null) {
                // 持有天数计算+费率+对应份额
                BigDecimal redeemFare = new BigDecimal(shareDetail.getRFare().toString());
                Integer confirm = dayMapper.selectNextWorkDay(realDate);
                shareDetail.setDDate(confirm);
                shareDetail.setNetvalue(value.doubleValue());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                for (ShareDetail detail : details) {
                    Integer holdDay = (int) (LocalDate.parse(confirm.toString(), formatter).toEpochDay() - LocalDate.parse(detail.getDDate().toString(), formatter).toEpochDay());
                    // 根据持有日期查询赎回费率
                    Map map = new HashMap(4);
                    map.put("fundcode", shareDetail.getFundcode());
                    map.put("holdday", holdDay);
                    FareRatio fareRatio = fareRatioMapper.selectFareRatioByPM(map);
                    BigDecimal curFare = new BigDecimal(detail.getRFare());
                    if (fareRatio == null) {
                        resultModel.setResult(false);
                        resultModel.setMsg("请设置基金[" + shareDetail.getFundcode() + "]的赎回费率！");
                        return resultModel;
                    }
                    // 用于静态份额刷新
                    StaticShare staticShare = new StaticShare();
                    staticShare.setFundcode(shareDetail.getFundcode());
                    staticShare.setNetvalue(detail.getNetvalue());
                    // 当前申购明细份额大于剩余赎回份额
                    if (curFare.compareTo(redeemFare) >= 0) {
                        curFare = curFare.subtract(redeemFare).setScale(4, BigDecimal.ROUND_HALF_UP);
                        detail.setRFare(curFare.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        BigDecimal decimal = new BigDecimal(String.valueOf(fareRatio.getRatio())).multiply(redeemFare).multiply(value).setScale(2, BigDecimal.ROUND_HALF_UP);
                        shareDetailMapper.updateShareDetail(detail);
                        // 赎回金额计算
                        shareDetail.setRedeem(decimal.doubleValue());
                        BigDecimal balance = redeemFare.multiply(value).subtract(decimal).setScale(2, BigDecimal.ROUND_HALF_UP);
                        // 本金计算
                        BigDecimal oriNetValue = new BigDecimal(String.valueOf(detail.getNetvalue()));
                        BigDecimal original = redeemFare.multiply(oriNetValue).setScale(2, BigDecimal.ROUND_HALF_UP);
                        // 设置属性
                        shareDetail.setOriginal(original.doubleValue());
                        shareDetail.setBalances(balance.doubleValue());
                        shareDetail.setRFare(redeemFare.doubleValue());
                        staticShare.setShares(redeemFare.doubleValue());
                        // 写入份额明细表
                        shareDetailMapper.insertShareDetailByDuplicate(shareDetail);
                        staticShares.add(staticShare);
                        break;
                    } else {
                        redeemFare = redeemFare.subtract(curFare);
                        detail.setRFare(0d);
                        // 已被赎回
                        detail.setBusinessType("7");
                        BigDecimal bigDecimal = new BigDecimal(String.valueOf(fareRatio.getRatio())).multiply(curFare).multiply(value).setScale(2, BigDecimal.ROUND_HALF_UP);
                        // 更新份额明细表
                        shareDetailMapper.updateShareDetail(detail);
                        // 赎回金额计算
                        BigDecimal balance = curFare.multiply(value).subtract(bigDecimal).setScale(2, BigDecimal.ROUND_HALF_UP);
                        // 本金计算
                        BigDecimal oriNetValue = new BigDecimal(String.valueOf(detail.getNetvalue()));
                        BigDecimal purchaseFare = new BigDecimal(String.valueOf(detail.getPurchase()));
                        BigDecimal original = curFare.multiply(oriNetValue).add(purchaseFare).setScale(2, BigDecimal.ROUND_HALF_UP);
                        // 属性设置
                        shareDetail.setOriginal(original.doubleValue());
                        shareDetail.setBalances(balance.doubleValue());
                        shareDetail.setRedeem(bigDecimal.doubleValue());
                        shareDetail.setRFare(curFare.doubleValue());
                        staticShare.setShares(curFare.doubleValue());
                        // 写入份额明细表
                        shareDetailMapper.insertShareDetailByDuplicate(shareDetail);
                        staticShares.add(staticShare);
                    }

                }
            }
            // 更新静态份额表，减去份额
            if (shareDetail.getBusinessType().equals("8")) {
                // 赎回暂存数据重新计算
                resultModel.setMsg("赎回信息暂存，等待净值更新！");
            } else {
                for(StaticShare staticShare:staticShares) {
                    staticShareMapper.updateStaticShare(staticShare);
                }
            }
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("基金赎回出错！", e);
            resultModel.setMsg("基金赎回出错！");
            resultModel.setResult(false);
            return resultModel;
        }
    }

    /**
     * 持仓金额+申购费用  / 持有份额 = 真实持仓成本
     *
     * @param fundcode
     * @return
     */
    @Override
    public ResultModel<Map> calculateProfit(String fundcode) {
        return null;
    }

    /**
     * 以10天的维度来更新净值，并刷新暂存的份额明细
     *
     * @param fundcode
     * @return
     */
    @Override
    public ResultModel updateHistoryNetValue(String fundcode) {
        ResultModel resultModel = new ResultModel();
        try {
            List<NetValue> netValues = handleHistoryNetValue(fundcode, 1, 10);
            Integer maxDate = netValueMapper.setlectMaxDate(fundcode);
            List<NetValue> collect = netValues.stream().filter((item) -> item.getCDate() > maxDate).collect(Collectors.toList());
            if (collect.size() > 0) {
                netValueMapper.batchInsert(collect);
            }
            // 处理申购暂存信息
            ShareDetail shareDetail = new ShareDetail();
            shareDetail.setFundcode(fundcode);
            shareDetail.setBusinessType("9");
            List<ShareDetail> shareDetails = shareDetailMapper.selectShareDetailByType(shareDetail);
            shareDetails.forEach(this::purchaseHandle);
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("更新历史净值出错！", e);
            resultModel.setMsg("更新历史净值出错！");
            resultModel.setResult(false);
            return resultModel;
        }
    }

    @Override
    public ResultModel<Map> selectCurNetValue(String fundcode) {
        ResultModel<Map> resultModel = new ResultModel<>();
        if (StringUtils.isEmpty(fundcode)) {
            return resultModel;
        }
        String url = "http://fundgz.1234567.com.cn/js/" + fundcode + ".js";
        Long date = Instant.now().toEpochMilli();
        Map<String, String> map = new HashMap<>(8);
        map.put("Host", "fundgz.1234567.com.cn");
        map.put("Referer", "http://fundf10.eastmoney.com/");
        try {
            String s = Http.doGet(url + "?rt=" + date, map);
            int start = s.indexOf("{");
            int end = s.lastIndexOf("}");
            String json = s.substring(start, end + 1);
            Map value = new ObjectMapper().readValue(json, Map.class);
            resultModel.setData(value);
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("查询实时净值信息出错！", e);
            resultModel.setMsg("查询实时净值出错！");
            resultModel.setResult(false);
            return resultModel;
        }
    }

    @Override
    public ResultModel<List<NetValue>> selectNetValue(String fundcode) {
        ResultModel<List<NetValue>> resultModel = new ResultModel<>();
        try {
            List<NetValue> netValues = netValueMapper.selectNetValue(fundcode);
            resultModel.setData(netValues);
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("查询累计净值信息出错！", e);
            resultModel.setMsg("查询累计净值信息出错！");
            resultModel.setResult(false);
            return resultModel;
        }
    }

    public void handleFundBriefly(FundInfo fundInfo) throws Exception {
        String url = "http://fundf10.eastmoney.com/jjfl_" + fundInfo.getFundcode() + ".html";
        StringBuffer content = Http.doGetBuffer(url, null);
        // 基金名称解析
        int nameL = content.indexOf("<title>");
        int nameR = content.indexOf("</title>");
        Pattern p = Pattern.compile("<title>(.+)\\(");
        Matcher m = p.matcher(content.substring(nameL, nameR));
        if (m.find()) {
            fundInfo.setFundname(m.group(1));
        }
        // 基金基本信息解析
        int mainL = content.lastIndexOf("<div class=\"bs_gl\">");
        int mainR = content.lastIndexOf("setInterval(function");
        String mainContent = content.substring(mainL, mainR);
        String replace = mainContent.replaceAll("\\s|\n", "");
        Pattern main = Pattern.compile("<label>(.+?)(<span>|<a.+?>)(.+?)(</a>|</span>)</label>");
        Matcher m2 = main.matcher(replace);
        int count = 1;
        while (m2.find()) {
            switch (count) {
                case 1:
                    fundInfo.setSetupDate(Integer.valueOf(m2.group(3).replace("-", "")));
                    break;
                case 2:
                    String director = m2.group(3);
                    if (director.contains("</a>")) {
                        int i = director.indexOf("<");
                        int i1 = director.lastIndexOf(">");
                        director = director.substring(0, i) + " " + director.substring(i1);
                    }
                    fundInfo.setDirector(director);
                    break;
                case 3:
                    fundInfo.setFundType(m2.group(3));
                    break;
                case 4:
                    fundInfo.setManger(m2.group(3));
                    break;
                case 5:
                    fundInfo.setAsset(m2.group(3));
                    break;
            }
            count++;
        }
        // 费率基础信息
        int fareL = content.indexOf("<div class=\"txt_in\">");
        int fareR = content.lastIndexOf("<div class=\"box nb\">");
        String fareContent = content.substring(fareL, fareR);
        Pattern fare = Pattern.compile("<td.+?>(.*?)</td><td.+?>(.*?)</td>");
        Matcher m3 = fare.matcher(fareContent);
        StringBuffer purchaseZone = new StringBuffer();
        StringBuffer redeemZone = new StringBuffer();
        while (m3.find()) {
            switch (m3.group(1)) {
                case "申购起点":
                    fundInfo.setPurchase(m3.group(2));
                    break;
                case "买入确认日":
                    fundInfo.setBDate(m3.group(2));
                    break;
                case "卖出确认日":
                    fundInfo.setSDate(m3.group(2));
                    break;
                case "管理费率":
                    fundInfo.setManageRatio(m3.group(2));
                    break;
                case "托管费率":
                    fundInfo.setTrusteeRatio(m3.group(2));
                    break;
                case "销售服务费率":
                    fundInfo.setServiceRatio(m3.group(2));
                    break;
                default:
                    if (m3.group(1).equals("---")) {
                        redeemZone.append(m3.group()).append("\n");
                    } else if (m3.group(2).contains("strike")) {
                        purchaseZone.append(m3.group()).append("\n");
                    }
            }
        }
        // 区间处理
        String pzone = purchaseZone.toString();
        String[] split = pzone.split("\n");
        StringBuffer sb = new StringBuffer();
        // 申购费率：<td class="th">小于100万元</td><td>---</td><td><strike class='gray'>1.00%</strike>&nbsp;|&nbsp;0.10%</td>
        boolean flag = split.length % 2 == 0;
        for (int i = flag ? split.length / 2 : 0; i < split.length; i++) {
            int i1 = split[i].indexOf("</td>");
            int i2 = split[i].lastIndexOf("</td>");
            if (i1 != -1 && i2 != -1) {
                sb.append(split[i].substring(15, i1)).append(":");
                sb.append(split[i].substring(i2 - 5, i2)).append("\n");
            }
        }
        fundInfo.setPurchaseFare(sb.toString());
        sb.delete(0, sb.length());
        // 赎回费率
        String rZone = redeemZone.toString();
        split = rZone.split("\n");
        // <td class="th">---</td><td>小于7天</td><td>1.50%</td>
        for (String str : split) {
            int i = str.indexOf("</td>");
            int i1 = str.lastIndexOf("</td>");
            int i2 = str.indexOf("</td>", i + 1);
            sb.append(str.substring(i + 9, i2)).append(":");
            sb.append(str.substring(i1 - 5, i1)).append("\n");
        }
        fundInfo.setRedeemFare(sb.toString());
    }

    public List<NetValue> handleHistoryNetValue(String fundcode, int start, int end) throws Exception {
        List<NetValue> list = new ArrayList<>();
        // 默认最近10年的历史净值
        String url = "http://api.fund.eastmoney.com/f10/lsjz?fundCode=" + fundcode + "&pageIndex=" + start + "&pageSize=" + end;
        // 需要Host和Referer头部
        Map<String, String> map = new HashMap<>(8);
        map.put("Host", "api.fund.eastmoney.com");
        map.put("Referer", "http://fundf10.eastmoney.com/");
        String json = Http.doGet(url, map);
        Map jsonObject = new ObjectMapper().readValue(json, Map.class);
        List<Map> list1 = (List<Map>) ((Map) jsonObject.get("Data")).get("LSJZList");
        list1.forEach((item) -> {
            NetValue netValue = new NetValue();
            netValue.setCDate(Integer.valueOf(((String) item.get("FSRQ")).replaceAll("-", "")));
            netValue.setFundcode(fundcode);
            netValue.setNetvalue(new BigDecimal((String) item.get("DWJZ")));
            String jzzzl = (String) item.get("JZZZL");
            netValue.setIncreaseratio(new BigDecimal(jzzzl.equals("") ? "0" : jzzzl));
            list.add(netValue);
        });
        return list;
    }
}
