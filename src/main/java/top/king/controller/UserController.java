package top.king.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.king.common.ApplicationInfo;
import top.king.common.BaseQuery;
import top.king.common.ResultModel;
import top.king.entity.User;
import top.king.entity.dto.UserSearchDTO;
import top.king.service.CommonService;
import top.king.service.UserService;
import utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    CommonService commonService;

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

    @RequestMapping("/img")
    public ResultModel<String> uploadFile(MultipartFile file, HttpServletRequest request,String username) {
        if (username != null) {
            String fileName = file.getOriginalFilename();
            String newFileName = username + fileName.substring(fileName.lastIndexOf("."));
            String path = ApplicationInfo.ROOT + StringUtils.fileParam("img", "user", newFileName);
            File img = new File(path);
            // 将文件保存在服务器
            ResultModel<String> resultModel = commonService.uploadImg(file, img);
            // 修改用户头像地址
            String url = "http://"+request.getHeader("host") + "/img/user/" + newFileName;
            userService.updateAvatar(username,url);
            // 返回url
            resultModel.setData(url);
            return resultModel;
        }else{
            return new ResultModel<>(false,"无用户信息!","");
        }
    }

    @RequestMapping("/avatar")
    public ResultModel<String> modifyAvatar(MultipartFile file, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String fileName = file.getOriginalFilename();
            String newFileName = authentication.getName() + fileName.substring(fileName.lastIndexOf("."));
            String path = ApplicationInfo.ROOT + StringUtils.fileParam("img", "user", newFileName);
            File img = new File(path);
            // 将文件保存在服务器
            ResultModel<String> resultModel = commonService.uploadImg(file, img);
            // 修改用户头像地址
            String url = "http://"+request.getHeader("host") + "/img/user/" + newFileName;
            userService.updateAvatar(authentication.getName(),url);
            // 返回url
            resultModel.setData(url);
            return resultModel;
        }else{
            return new ResultModel<>(false,"无用户信息!","");
        }
    }
}
