package com.lkc.www.config;

import com.lkc.www.interceptor.LoginAuthInterceptor;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器实现路径拦截
 */
@Component
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Resource
    private LoginAuthInterceptor loginAuthInterceptor;

    //拦截器注册
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginAuthInterceptor)
                .excludePathPatterns(//对这两个路径排除
                        "/admin/system/index/login",
                        "/admin/system/index/generateValidateCode"
                ).addPathPatterns("/**");//对所有路径都拦截
    }
}
