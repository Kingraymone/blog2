package top.king.controller.authority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.king.common.BaseQuery;
import top.king.common.ResultModel;
import top.king.entity.authority.Menu;
import top.king.service.authority.MenuService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    MenuService menuService;

    @RequestMapping("/search")
    public ResultModel<List<Menu>> selectMenus(@RequestBody BaseQuery<Map> param) {
        return menuService.queryMenus(param);
    }

    @RequestMapping("/add")
    public ResultModel insertMenu(@RequestBody Menu menu) {
        return menuService.insertMenu(menu);
    }

    @RequestMapping("/delete")
    public ResultModel deleteMenu(@RequestBody ArrayList<String> primaryKey) {
        return menuService.deleteMenu(primaryKey);
    }

    @RequestMapping("/update")
    public ResultModel updateMenu(@RequestBody Menu menu) {
        return menuService.updateMenu(menu);
    }

}
