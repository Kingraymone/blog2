package top.king.mapper;


import top.king.common.BaseQuery;
import top.king.entity.User;
import top.king.entity.dto.UserSearchDTO;

import java.util.List;

public interface UserMapper {
    List<User> selectUsers(BaseQuery<UserSearchDTO> param);

    void deleteUser(String primaryKey);

    void insertUser(User user);

    void updateUser(User user);

    User verifyUser(User user);
}
