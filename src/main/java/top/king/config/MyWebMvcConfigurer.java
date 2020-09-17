package top.king.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.king.common.ApplicationInfo;
import top.king.common.VueTestInterceptor;
import utils.StringUtils;

@Component
public class MyWebMvcConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new VueTestInterceptor());
    }

    /**
     * 允许跨域
     *
     * @param registry
     * @return void
     * @description
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("Accept", "Origin", "X-Requested-With", "Content-Type",
                        "Last-Modified", "device", "token")
                .allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // resourceLocations: 以/截位路径  如 /a/b -> /a   /a/b/ -> /a/b
        // 路径需加上协议，如file、classpath
        registry.addResourceHandler("/img/**").addResourceLocations("file:" + ApplicationInfo.ROOT + StringUtils.fileParam("img/"));
    }
}
