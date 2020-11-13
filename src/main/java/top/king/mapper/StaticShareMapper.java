package top.king.mapper;

import top.king.entity.StaticShare;

public interface StaticShareMapper {
    int insert(StaticShare record);

    int insertSelective(StaticShare record);
}