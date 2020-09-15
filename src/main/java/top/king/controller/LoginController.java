package top.king.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.king.common.ResultModel;
import top.king.entity.User;
import top.king.service.UserService;

import java.util.List;

@RestController
public class LoginController {
    @Autowired
    UserService userService;
    @RequestMapping("/login")
    public ResultModel<List<User>> login() {
        //boolean flag = user.getUsername().equals("king") && user.getPassword().equals("123456");
        //List<User> users = userService.selectUsers();
        //return new ResultModel<>(users,"312312");
        return null;
    }
}
