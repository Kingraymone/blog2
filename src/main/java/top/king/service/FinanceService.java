package top.king.service;

import top.king.common.BaseQuery;
import top.king.common.ResultModel;
import top.king.entity.FundInfo;
import top.king.entity.NetValue;

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
     * 基金模拟买入实现  展示要点：持有天数，收益，收益率
     * 基金费率表：申购、赎回费率
     * 基金静态份额表：基金代码、申请日期、业务标志(申购赎回)
     * 基金份额明细表：记录每笔申购、赎回
     */
}
