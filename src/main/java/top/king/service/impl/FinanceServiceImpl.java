package top.king.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sun.deploy.net.HttpUtils;
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
import java.math.MathContext;
import java.time.Instant;
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
            // 基金基本信息
            handleFundBriefly(fundInfo);
            // 插入基金信息表
            fundInfoMapper.deleteFundByFundCode(fundInfo);
            fundInfoMapper.insert(fundInfo);
            // 历史净值获取
            List<NetValue> netValues = handleHistoryNetValue(fundcode,1,3650);
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
    }

    @Override
    public ResultModel purchaseHandle(ShareDetail shareDetail) {
        // 申购金额+申购日期+基金代码
        ResultModel resultModel = new ResultModel();
        try {
            // 申购日期净值获得，不存在净值则暂存份额明细表，等待净值更新后重新计算
            NetValue netValue = new NetValue();
            netValue.setCDate(shareDetail.getDRequest());
            netValue.setFundcode(shareDetail.getFundcode());
            List<NetValue> netValues = netValueMapper.selectByNetvalue(netValue);
            BigDecimal value = null;
            if (netValues.size() < 1) {
                shareDetail.setBusinessType("9");
            } else {
                value = netValues.get(0).getNetvalue();
            }
            if (value != null) {
                // 根据申购金额获得申购费率，不存在返回FALSE
                Map<String, String> map = new HashMap<>(4);
                map.put("balance", String.valueOf(shareDetail.getPBalance()));
                map.put("fundcode", shareDetail.getFundcode());
                FareRatio fareRatio = fareRatioMapper.selectFareRatioByPM(map);
                if(fareRatio==null){
                    resultModel.setResult(false);
                    resultModel.setMsg("请维护基金["+map.get("fundcode")+"]的费率信息！");
                    return resultModel;
                }
                // 计算申购费用
                BigDecimal ratio = new BigDecimal(String.valueOf(fareRatio.getRatio()));
                BigDecimal balance = new BigDecimal(String.valueOf(shareDetail.getPBalance()));
                BigDecimal fare = balance.divide((ratio.add(new BigDecimal(1))),4,BigDecimal.ROUND_HALF_UP).multiply(ratio).setScale(8,BigDecimal.ROUND_HALF_UP);
                shareDetail.setPurchase(fare.doubleValue());
                // 计算申购份额
                BigDecimal share = balance.subtract(fare).divide(value,2,BigDecimal.ROUND_HALF_UP);
                shareDetail.setShares(share.doubleValue());
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
                BigDecimal bigDecimal = new BigDecimal(String.valueOf(shareDetail.getPBalance()));
                BigDecimal bigDecimal1 = new BigDecimal(String.valueOf(shareDetail.getPurchase()));
                staticShare.setBalances(bigDecimal.subtract(bigDecimal1).doubleValue());
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

    @Override
    public ResultModel redeemHandle(ShareDetail shareDetail) {
        return null;
    }

    @Override
    public ResultModel<Map> calculateProfit(String fundcode) {
        return null;
    }

    /**
     * 以10天的维度来更新
     * @param fundcode
     * @return
     */
    @Override
    public ResultModel updateHistoryNetValue(String fundcode) {
        ResultModel resultModel = new ResultModel();
        try {
            List<NetValue> netValues = handleHistoryNetValue(fundcode, 1, 10);
            Integer maxDate = netValueMapper.setlectMaxDate();
            List<NetValue> collect = netValues.stream().filter((item) -> {
                return item.getCDate() > maxDate;
            }).collect(Collectors.toList());
            netValueMapper.batchInsert(netValues);
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

    public List<NetValue> handleHistoryNetValue(String fundcode,int start,int end) throws Exception {
        List<NetValue> list = new ArrayList<>();
        // 默认最近10年的历史净值
        String url = "http://api.fund.eastmoney.com/f10/lsjz?fundCode=" + fundcode + "&pageIndex="+start+"&pageSize="+end;
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
            netValue.setIncreaseratio(new BigDecimal(jzzzl.equals("") ? 0 : Double.parseDouble(jzzzl)));
            list.add(netValue);
        });
        return list;
    }
}
