package top.king.config.security;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import utils.Convert;
import utils.JwtTokenUtil;
import utils.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
        } else {
            SecurityContextHolder.getContext().setAuthentication(token);
            filterChain.doFilter(request, response);
        }
    }

    private UsernamePasswordAuthenticationToken createToken(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION);
        if (StringUtils.isEmpty(header)) {
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
        try {
            Jws<Claims> claimsJws = JwtTokenUtil.parseToken(token);
            assert claimsJws != null;
            Claims body = claimsJws.getBody();
            String uniqueId = Convert.hex2Str((String) body.get(Convert.str2Hex("uniqueId")));
            String username = Convert.hex2Str((String) body.get(Convert.str2Hex("username")));
            // 权限信息转换，后续根据uniqueId从redis中获取
            Set<Map> maps = new ObjectMapper().readValue(Convert.hex2Str((String) body.get(Convert.str2Hex("authorities"))), Set.class);
            List<SimpleGrantedAuthority> authorities = maps.stream().map((map) -> new SimpleGrantedAuthority("ROLE_" + map.get("authority"))).collect(Collectors.toList());
            // 测试中，只验证权限
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
            return usernamePasswordAuthenticationToken;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

}
