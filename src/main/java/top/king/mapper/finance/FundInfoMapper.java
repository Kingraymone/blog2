package top.king.mapper.finance;

import top.king.common.BaseQuery;
import top.king.entity.finance.FundInfo;

import java.util.List;
import java.util.Map;

public interface FundInfoMapper {
    int insert(FundInfo record);

    int insertSelective(FundInfo record);

    List<FundInfo> selectFundInfo(BaseQuery<FundInfo> param);

    void deleteFunds(List<String> primaryKey);

    void deleteFundByFundCode(FundInfo fundInfo);

    void updateFundInfo(FundInfo fundInfo);

    List<Map> selectFundDict();
}