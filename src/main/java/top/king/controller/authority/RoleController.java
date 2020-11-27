package top.king.controller.authority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.king.common.BaseQuery;
import top.king.common.ResultModel;
import top.king.entity.authority.Role;
import top.king.entity.dto.AuthorityDTO;
import top.king.service.authority.RoleService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @RequestMapping("/search")
    public ResultModel<List<Role>> selectRoles(@RequestBody BaseQuery<Map> param) {
        return roleService.queryRoles(param);
    }

    @RequestMapping("/add")
    public ResultModel insertRole(@RequestBody Role role) {
        return roleService.insertRole(role);
    }

    @RequestMapping("/delete")
    public ResultModel deleteRole(@RequestBody ArrayList<String> primaryKey) {
        return roleService.deleteRole(primaryKey);
    }

    @RequestMapping("/update")
    public ResultModel updateRole(@RequestBody Role role) {
        return roleService.updateRole(role);
    }

    @RequestMapping("/authority")
    public ResultModel<Map<String, List>> queryAuthority(String roleId) {
        ResultModel<Map<String, List>> resultModel = new ResultModel<>();
        Map<String, List> map = new HashMap<>(4);
        ResultModel<List<Map>> queryAuthority = roleService.queryAuthority();
        ResultModel<List<List>> roleAuthority = roleService.queryRoleAuthority(roleId, queryAuthority.getData());
        map.put("role", roleAuthority.getData());
        map.put("all", queryAuthority.getData());
        resultModel.setData(map);
        resultModel.setResult(roleAuthority.getResult() && queryAuthority.getResult());
        resultModel.setMsg(roleAuthority.getResult() ? queryAuthority.getMsg() : roleAuthority.getMsg());
        return resultModel;
    }

    @RequestMapping("/updateAuthority")
    public ResultModel updateAuthority(@RequestBody AuthorityDTO authorityDTO) {
        return roleService.updateAuthority(authorityDTO);
    }
}
