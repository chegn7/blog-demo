package xyz.cheng7.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xyz.cheng7.blog.handler.LoginInterceptor;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    /**
     * 跨域配置用handler里的CrosFilter来实现
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
//         跨域配置
        registry.addMapping("/**")
                .allowedMethods("GET", "POST")
                .allowedOrigins("http://localhost:8080");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/test/login")
                .addPathPatterns("/comments/create/change")
                .addPathPatterns("/articles/publish");
    }
}
