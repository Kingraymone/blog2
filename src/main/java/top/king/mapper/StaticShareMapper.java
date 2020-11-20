package top.king.mapper;

import top.king.entity.StaticShare;

public interface StaticShareMapper {
    int insert(StaticShare record);

    int insertSelective(StaticShare record);

    void insertStaticShareByDuplicate(StaticShare staticShare);

    void updateStaticShare(StaticShare staticShare);

    StaticShare selectStaticShare(StaticShare staticShare);
}