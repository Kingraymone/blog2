package top.king.mapper;

import top.king.common.BaseQuery;
import top.king.entity.Resources;

import java.util.List;
import java.util.Map;

public interface ResourcesMapper {
    int insert(Resources record);

    int insertSelective(Resources record);

    List<Resources> queryResources(BaseQuery<Map> param);

    void deleteResources(List<String> primaryKey);

    void updateResources(Resources resources);

    List<Integer> selectAllByResourceCode(List<String> resources);
}