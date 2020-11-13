package top.king.mapper;

import top.king.entity.Role;

import java.util.List;

public interface RoleMapper {
    int insert(Role record);

    int insertSelective(Role record);

    List<String> selectRoles(String id);
}