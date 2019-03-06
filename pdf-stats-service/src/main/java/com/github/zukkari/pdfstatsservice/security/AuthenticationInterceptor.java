package com.github.zukkari.pdfstatsservice.security;

import com.github.zukkari.stats.client.StatisticsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.HttpURLConnection;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Value("${auth.token}")
    private String token;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Handling request {}", request);
        String header = request.getHeader(StatisticsClient.STATS_HEADER);
        String headerValue = header == null ? "" : header;

        boolean allowed = token.equals(headerValue);
        log.info("Header value is '{}'. Allowed to pass: {}", headerValue, allowed);

        if (!allowed) {
            response.setStatus(HttpURLConnection.HTTP_UNAUTHORIZED);
        }

        return allowed;
    }
}
