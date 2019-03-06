package com.github.zukkari.pdfstatsservice.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Value("${auth.token}")
    private String token;

    private static final String STATS_HEADER = "stats-auth";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Handling request {}", request);
        String headerValue = request.getHeader(STATS_HEADER);

        boolean decision = token.equals(headerValue);
        log.info("Header value is '{}'. Allowed to pass: {}", headerValue, decision);

        return decision;
    }
}
