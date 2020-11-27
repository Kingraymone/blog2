package top.king.mapper.finance;

import top.king.entity.finance.StaticShare;

public interface StaticShareMapper {
    int insert(StaticShare record);

    int insertSelective(StaticShare record);

    void insertStaticShareByDuplicate(StaticShare staticShare);

    void updateStaticShare(StaticShare staticShare);

    StaticShare selectStaticShare(StaticShare staticShare);
}