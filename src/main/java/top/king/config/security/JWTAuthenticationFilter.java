package top.king.config.security;


import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import utils.JwtTokenUtil;
import utils.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * 对包含Authorization的请求过滤
 * 如果包含则进行校验并创建Authentication(包括权限的获取)令牌
 *
 * @package top.king.config.security
 * @date 2020-10-13
 */
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 从请求中获取令牌
        UsernamePasswordAuthenticationToken token = createToken(request);
        // 如果不存在令牌则跳过当前过滤器
        if (ObjectUtils.isEmpty(token)) {
            filterChain.doFilter(request, response);
        }
    }

    private UsernamePasswordAuthenticationToken createToken(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION);
        if (StringUtils.isEmpty(header.trim())) {
            return null;
        }
        // 存在Authorization头则进行验证，失败则抛出异常
        if (!JwtTokenUtil.validateToken(header)) {
            throw new AuthenticationServiceException("jwt 校验失败!");
        }
        // 生成令牌
        return createAuthentication(header);
    }

    private UsernamePasswordAuthenticationToken createAuthentication(String token) {
        return new UsernamePasswordAuthenticationToken("king", "123456");
    }

}
