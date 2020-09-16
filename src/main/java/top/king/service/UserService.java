package top.king.service;

import top.king.common.BaseQuery;
import top.king.common.ResultModel;
import top.king.entity.User;
import top.king.entity.dto.UserSearchDTO;

import java.util.List;

public interface UserService {
    ResultModel<List<User>> selectUsers(BaseQuery<UserSearchDTO> param);

    ResultModel addUser(User user);

    ResultModel updateUser(User user);

    ResultModel deleteUser(List<String> primaryKey);

    ResultModel<Boolean> verifyUser(User user);
}
