package top.king.service.authority;

import top.king.common.BaseQuery;
import top.king.common.ResultModel;
import top.king.entity.authority.Role;
import top.king.entity.dto.AuthorityDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface RoleService {
    ResultModel insertRole(Role record);

    ResultModel<List<Role>> queryRoles(BaseQuery<Map> param);

    ResultModel deleteRole(List<String> primaryKey);

    ResultModel updateRole(Role role);

    /**
     * 权限查询
     *
     * @return top.king.common.ResultModel<java.util.List < java.util.Map>>
     * @date 2020/11/27
     */
    public ResultModel<List<Map>> queryAuthority();

    public ResultModel<List<List>> queryRoleAuthority(String role, List<Map> all);

    /**
     * 权限更新
     *
     * @param authorityDTO
     * @return top.king.common.ResultModel
     * @date 2020/11/27
     */
    public ResultModel updateAuthority(AuthorityDTO authorityDTO);
}
