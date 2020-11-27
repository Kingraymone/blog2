package top.king.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import top.king.entity.authority.User;
import top.king.mapper.authority.RoleMapper;
import top.king.service.authority.UserService;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 从数据库中获得用户信息
 *
 * @package top.king.config.security
 * @date 2020-09-26
 */
@Component
public class JwtUserService implements UserDetailsService {
    @Autowired
    UserService userService;
    @Autowired
    RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 加载用户信息
        User user = userService.loadUserInfo(username);
        // 加载权限信息
        List<String> roles = roleMapper.selectRoles(String.valueOf(user.getUniqueId()));
        List<SimpleGrantedAuthority> authorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        JwtUser jwtUser = new JwtUser(username, user.getPassword(), authorities);
        user.setPassword("");
        jwtUser.setUser(user);
        return jwtUser;
    }
}
