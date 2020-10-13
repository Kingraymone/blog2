package top.king.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;
import top.king.common.ResultModel;
import top.king.entity.User;
import utils.JwtTokenUtil;
import utils.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;


/**
 * jwt登陆过滤器
 *
 * @package top.king.config.security
 * @date 2020-09-26
 */
public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {
    /**
     * 设置过滤的url及method
     */
    protected JwtLoginFilter() {
        super(new AntPathRequestMatcher("/user/verify", "POST"));
    }

    /**
     * 身份验证
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        // 从请求体中获得用户信息
        String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        String username = null, password = null;
        if (!StringUtils.isEmpty(body)) {
            Map<String, String> map = new ObjectMapper().readValue(body, Map.class);
            username = map.get("username");
            password = map.get("password");
        }
        username = username == null ? "" : username.trim();
        password = password == null ? "" : password;
        // 将请求中的参数转换为Authentication
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        // 交付给AuthenticationManger验证
        return this.getAuthenticationManager().authenticate(token);
    }

    /**
     * 验证成功处理
     *
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        JwtUser jwtUser = (JwtUser) authResult.getPrincipal();
        // 获得令牌
        String token = JwtTokenUtil.generateToken(jwtUser.getUser());
        response.setHeader("Authorization", token);
        ResultModel resultModel = new ResultModel(true);
        resultModel.setData(jwtUser.getUser());
        responseToClient(response, resultModel);
    }

    /**
     * 验证失败处理
     *
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        ResultModel resultModel = new ResultModel(false);
        resultModel.setMsg("用户名密码错误！");
        responseToClient(response, resultModel);
    }

    private void responseToClient(HttpServletResponse response, ResultModel resultModel) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().println(new ObjectMapper().writeValueAsString(resultModel));
    }
}
