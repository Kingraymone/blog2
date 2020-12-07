package top.king.mapper.finance;

import top.king.entity.finance.NetValue;

import java.util.List;

public interface NetValueMapper {
    int insert(NetValue record);

    int insertSelective(NetValue record);

    int batchInsert(List<NetValue> list);

    void deleteNetValues(NetValue newValue);

    void deleteNetValuesByFundcode(List<String> primaryKey);

    List<NetValue> selectNetValue(String fundcode);

    List<NetValue> selectByNetvalue(NetValue newValue);

    Integer setlectMaxDate(String fundcode);
}