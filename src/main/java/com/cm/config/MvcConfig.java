package com.cm.config;

import com.cm.utils.LoginInterceptor;
import com.cm.utils.RefreshTokenInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //refresh token
        registry.addInterceptor(new RefreshTokenInterceptor(stringRedisTemplate)).
                addPathPatterns("/**").order(0);

        //login prevent
        registry.addInterceptor(new LoginInterceptor())
                .excludePathPatterns(
                        "/user/code",
                        "/user/login",
                        "/user/register",
                        "/user/resetPasswd",
                        "/user/math/{level}",
                        "/user/poem/kid",
                        "/user/poem/teen"
                ).order(1);
    }
}
