package top.king.service;

import top.king.common.BaseQuery;
import top.king.common.ResultModel;
import top.king.entity.FundInfo;
import top.king.entity.NetValue;
import top.king.entity.ShareDetail;

import javax.xml.transform.Result;
import java.util.List;
import java.util.Map;

public interface FinanceService {
    ResultModel addFund(String fundcode);

    ResultModel<List<FundInfo>> selectFundInfo(BaseQuery<FundInfo> param);

    ResultModel deleteFundInfos(List<String> primaryKey);

    ResultModel<List<NetValue>> selectNetValue(String fundcode);

    ResultModel<List> selectFundDict();

    /**
     * 根据基金代码查询实时单位净值
     * @param fundcode
     * @return
     */
    ResultModel<Map> selectCurNetValue(String fundcode);

    /**
     * 更新当前基金的历史净值
     * @param fundcode
     * @return
     */
    ResultModel updateHistoryNetValue(String fundcode);

    /**
     * 申购处理
     * @param shareDetail
     * @return
     */
    ResultModel purchaseHandle(ShareDetail shareDetail);

    /**
     * 赎回处理
     * @param shareDetail
     * @return
     */
    ResultModel redeemHandle(ShareDetail shareDetail);

    /**
     * 计算收益情况
     * @param fundcode
     * @return
     */
    ResultModel<Map> calculateProfit(String fundcode);

    /**
     * 基金模拟买入实现  展示要点：持有天数，收益，收益率
     * 基金费率表：申购、赎回费率
     * 基金静态份额表：基金代码、申请日期、业务标志(申购赎回)
     * 基金份额明细表：记录每笔申购、赎回
     */
}
