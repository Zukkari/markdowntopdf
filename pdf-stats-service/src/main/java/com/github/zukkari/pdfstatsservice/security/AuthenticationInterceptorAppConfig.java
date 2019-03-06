package com.github.zukkari.pdfstatsservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class AuthenticationInterceptorAppConfig implements WebMvcConfigurer {

    private final AuthenticationInterceptor authInterceptor;

    @Autowired
    public AuthenticationInterceptorAppConfig(AuthenticationInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor);
    }
}
