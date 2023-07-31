package com.sunyi.laundryms.web.config;

import com.sunyi.laundryms.web.filter.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Autowired
    private TokenInterceptor tokenInterceptor;
    /**
     * 配置拦截器、拦截路径
     * 每次请求到拦截的路径，就会去执行拦截器中的方法
     * @param
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePath = new ArrayList<>();
        //排除拦截，除了登录，其他都拦截
        excludePath.add("/login/**");
        excludePath.add("/index/**");
        excludePath.add("/admin/**");
        excludePath.add("/user/**");
//        excludePath.add("/user/uploadPicture");
//        excludePath.add("/user/updateUserInfo");
        excludePath.add("/css/**");
        excludePath.add("/js/**");
        excludePath.add("/img/**");
        excludePath.add("/lib/**");
        excludePath.add("/scss/**");
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePath);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
