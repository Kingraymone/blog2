package top.king.service;

import top.king.common.BaseQuery;
import top.king.common.ResultModel;
import top.king.entity.FundInfo;

import java.util.List;

public interface FinanceService {
    ResultModel addFund(String fundcode);

    ResultModel<List<FundInfo>> selectFundInfo(BaseQuery<FundInfo> param);
}
