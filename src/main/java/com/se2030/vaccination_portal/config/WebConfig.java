package com.se2030.vaccination_portal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns(
                        "/dashboard",
                        "/inventory/**",
                        "/appointments/**",
                        "/dose-records/**",
                        "/centers/**",
                        "/adverse-reactions/**",
                        "/reports/**"
                );
    }
}
