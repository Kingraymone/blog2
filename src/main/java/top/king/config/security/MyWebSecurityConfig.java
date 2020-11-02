package top.king.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@EnableGlobalMethodSecurity(prePostEnabled=true)
@Configuration
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    JwtUserService jwtUserService;

    /**
     * 关闭默认配置
     */
    protected MyWebSecurityConfig() {
        super(true);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 配置查询用户服务，使用默认DaoAuthenticationProvider来验证
        auth.userDetailsService(jwtUserService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 禁用跨站请求伪造和Session管理
        http.csrf().disable().sessionManagement().disable();
        // 跨域支持
        http.cors();
        // 统一异常处理+访问权限控制
        http.exceptionHandling().accessDeniedHandler(new MyAccessDeniedHandlerImpl())
                .authenticationEntryPoint(new MyAuthenticationEntryPoint())
                .and()
                .authorizeRequests()
                .antMatchers("/user/**").hasRole("SYSTEM");
        // 登陆过滤器
        JwtLoginFilter loginFilter = new JwtLoginFilter();
        // 设置身份管理验证器
        loginFilter.setAuthenticationManager(authenticationManager());
        // 添加过滤器
        http.addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter.class);
        // jwt请求过滤器
        http.addFilterBefore(new JWTAuthenticationFilter(), FilterSecurityInterceptor.class);
    }

    /**
     * 添加跨域支持
     *
     * @return
     */
    @Bean("corsFilter")
    public CorsFilter createCorsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addExposedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 对任意请求进行跨域过滤
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }
}
