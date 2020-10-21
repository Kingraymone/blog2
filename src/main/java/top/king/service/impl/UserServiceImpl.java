package top.king.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import top.king.common.BaseQuery;
import top.king.common.BaseService;
import top.king.common.ResultModel;
import top.king.entity.User;
import top.king.entity.dto.UserSearchDTO;
import top.king.mapper.UserMapper;
import top.king.service.UserService;

import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl extends BaseService implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public ResultModel<List<User>> selectUsers(BaseQuery<UserSearchDTO> param) {
        ResultModel<List<User>> resultModel = new ResultModel<>();
        try {
            PageHelper.startPage(param.getPageNum(), param.getPageSize());
            Page<User> users = (Page<User>) userMapper.selectUsers(param);
            resultModel.setData(users);
            resultModel.setCount(users.getTotal());
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("查询用户资料出错！", e);
            resultModel.setMsg("查询用户资料出错！");
            resultModel.setResult(false);
            return resultModel;
        }
    }

    @Override
    public ResultModel addUser(User user) {
        ResultModel resultModel = new ResultModel();
        try {
            userMapper.insertUser(user);
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("添加用户出错！", e);
            resultModel.setMsg("添加用户出错！");
            resultModel.setResult(false);
            return resultModel;
        }
    }

    @Override
    public ResultModel updateUser(User user) {
        ResultModel resultModel = new ResultModel();
        try {
            userMapper.updateUser(user);
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("修改用户出错！", e);
            resultModel.setMsg("修改用户出错！");
            resultModel.setResult(false);
            return resultModel;
        }
    }

    @Override
    public ResultModel deleteUser(List<String> primaryKey) {
        ResultModel resultModel = new ResultModel();
        try {
            userMapper.deleteUser(primaryKey);
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("删除用户出错！", e);
            resultModel.setMsg("删除用户出错！");
            resultModel.setResult(false);
            return resultModel;
        }
    }

    @Override
    public ResultModel<Boolean> verifyUser(User user) {
        ResultModel<Boolean> resultModel = new ResultModel<>();
        try {
            User user1 = userMapper.verifyUser(user);
            if (ObjectUtils.isEmpty(user1)) {
                resultModel.setData(Boolean.FALSE);
            } else {
                resultModel.setData(Boolean.TRUE);
            }
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("验证用户出错！", e);
            resultModel.setMsg("验证用户出错！");
            resultModel.setResult(Boolean.FALSE);
            return resultModel;
        }
    }

    @Override
    public User loadUserInfo(String username) {
        User user = null;
        try {
            user = userMapper.loadUserInfo(username);
        } catch (Exception e) {
            bLogger.debug("加载用户信息出错！", e);
        }
        return user;
    }

    @Override
    public void updateAvatar(String username, String url) {
        try {
            HashMap<String, String> map = new HashMap<>(4);
            map.put("username", username);
            map.put("avatar", url);
            userMapper.updateAvatar(map);
        } catch (Exception e) {
            bLogger.debug("修改用户头像出错！", e);
        }
    }
}
