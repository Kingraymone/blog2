package top.king.mapper.authority;


import top.king.common.BaseQuery;
import top.king.entity.authority.User;
import top.king.entity.dto.UserSearchDTO;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    List<User> selectUsers(BaseQuery<UserSearchDTO> param);

    void deleteUser(List<String> primaryKey);

    void insertUser(User user);

    void updateUser(User user);

    User verifyUser(User user);

    User loadUserInfo(String user);

    void updateAvatar(Map map);
}
