package top.king.mapper.authority;

import top.king.common.BaseQuery;
import top.king.entity.authority.Menu;

import java.util.List;
import java.util.Map;

public interface MenuMapper {
    int insert(Menu record);

    int insertSelective(Menu record);

    List<Menu> queryMenus(BaseQuery<Map> param);
    void deleteMenu(List<String> primaryKey);
    void updateMenu(Menu menu);
}