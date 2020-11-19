package top.king.mapper;

import top.king.entity.NetValue;

import java.util.List;

public interface NetValueMapper {
    int insert(NetValue record);

    int insertSelective(NetValue record);

    int batchInsert(List<NetValue> list);

    void deleteNetValues(NetValue newValue);

    List<NetValue> selectNetValue(String fundcode);

    List<NetValue> selectByNetvalue(NetValue newValue);

    Integer setlectMaxDate(String fundcode);
}