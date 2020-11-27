package top.king.service.authority;

import top.king.common.BaseQuery;
import top.king.common.ResultModel;
import top.king.entity.authority.Menu;

import java.util.List;
import java.util.Map;

public interface MenuService {
    ResultModel<List<Menu>> queryMenus(BaseQuery<Map> param);

    ResultModel deleteMenu(List<String> primaryKey);

    ResultModel updateMenu(Menu menu);

    ResultModel insertMenu(Menu record);
}
