package jjie.lbsystem.config;

import jjie.lbsystem.component.MyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private MyInterceptor myInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        // 登录页面
                        "/login.html",
                        // 登录请求
                        "/user/login",

                        // 允许未登录先打开的页面
                        "/book_list.html",

                        // 静态资源
                        "/css/**",
                        "/js/**",
                        "/pic/**"
                );
    }
}
