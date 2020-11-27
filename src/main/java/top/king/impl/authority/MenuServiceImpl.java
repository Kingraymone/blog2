package top.king.impl.authority;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.king.common.BaseQuery;
import top.king.common.BaseService;
import top.king.common.ResultModel;
import top.king.entity.authority.Menu;
import top.king.mapper.authority.MenuMapper;
import top.king.service.authority.MenuService;

import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl extends BaseService implements MenuService {
    @Autowired
    MenuMapper menuMapper;

    @Override
    public ResultModel<List<Menu>> queryMenus(BaseQuery<Map> param) {
        ResultModel<List<Menu>> resultModel = new ResultModel<>();
        try {
            PageHelper.startPage(param.getPageNum(), param.getPageSize());
            Page<Menu> menus = (Page<Menu>) menuMapper.queryMenus(param);
            resultModel.setData(menus);
            resultModel.setCount(menus.getTotal());
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("查询菜单信息出错！", e);
            resultModel.setMsg("查询菜单信息出错！");
            resultModel.setResult(false);
            return resultModel;
        }
    }

    @Override
    public ResultModel deleteMenu(List<String> primaryKey) {
        ResultModel resultModel = new ResultModel();
        try {
            menuMapper.deleteMenu(primaryKey);
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("删除菜单出错！", e);
            resultModel.setMsg("删除菜单出错！");
            resultModel.setResult(false);
            return resultModel;
        }
    }

    @Override
    public ResultModel updateMenu(Menu menu) {
        ResultModel resultModel = new ResultModel();
        try {
            menuMapper.updateMenu(menu);
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("修改菜单出错！", e);
            resultModel.setMsg("修改菜单出错！");
            resultModel.setResult(false);
            return resultModel;
        }
    }

    @Override
    public ResultModel insertMenu(Menu record) {
        ResultModel resultModel = new ResultModel();
        try {
            menuMapper.insertSelective(record);
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("添加菜单出错！", e);
            resultModel.setMsg("添加菜单出错！");
            resultModel.setResult(false);
            return resultModel;
        }
    }
}
