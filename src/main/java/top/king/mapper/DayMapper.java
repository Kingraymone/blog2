package top.king.mapper;

import top.king.entity.Day;

import java.util.Map;

public interface DayMapper {
    int insert(Day record);

    int insertSelective(Day record);

    void insertInitDay(Map map);

    Integer selectNextWorkDay(Integer cur);

    /**
     * 查找正式申请日期，如周末申请，真实申请日期为周一
     * @param date
     * @return
     */
    Integer selectRealDate(Integer date);
}