package top.king.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.king.common.BaseQuery;
import top.king.common.ResultModel;
import top.king.entity.User;
import top.king.entity.dto.UserSearchDTO;
import top.king.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/search")
    public ResultModel<List<User>> selectUsers(@RequestBody BaseQuery<UserSearchDTO> param) {
        return userService.selectUsers(param);
    }

    @RequestMapping("/add")
    public ResultModel addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @RequestMapping("/delete")
    public ResultModel deleteUser(@RequestBody UserSearchDTO userSearchDTO) {
        return userService.deleteUser(userSearchDTO.getPrimaryKey());
    }

    @RequestMapping("/update")
    public ResultModel updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @RequestMapping("/verify")
    public ResultModel<Boolean> verifyUser(@RequestBody User user) {
        return userService.verifyUser(user);
    }
}
