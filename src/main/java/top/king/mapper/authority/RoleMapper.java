package top.king.mapper.authority;

import top.king.common.BaseQuery;
import top.king.entity.authority.Role;
import top.king.entity.dto.AuthorityDTO;

import java.util.List;
import java.util.Map;

public interface RoleMapper {
    int insert(Role record);

    int insertSelective(Role record);

    List<String> selectRoles(String id);

    List<Role> queryRoles(BaseQuery<Map> param);

    void deleteRole(List<String> primaryKey);

    void updateRole(Role role);

    void insertAuthority(AuthorityDTO authorityDTO);

    void deleteAuthority(Integer role_id);

    List<Map<String, String>> queryAuthority();

    List<String> queryAuthorityCode(String roleId);
}