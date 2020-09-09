package top.king.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.king.common.ResultModel;
import top.king.entity.User;

@RestController
public class LoginController {
    @RequestMapping("/login")
    public ResultModel login(@RequestBody User user) {
        boolean flag = user.getUsername().equals("king") && user.getPassword().equals("123456");
        return new ResultModel(flag);
    }
}
