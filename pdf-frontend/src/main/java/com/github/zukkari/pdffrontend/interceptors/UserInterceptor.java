package com.github.zukkari.pdffrontend.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class UserInterceptor implements HandlerInterceptor {
    public static final String ATTRIBUTE_AUTHENTICATED = "attr_authenticated";
    public static final String ATTRIBUTE_USERNAME = "attr_username";

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) {

        if (modelAndView == null) {
            return;
        }

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            log.info("Authentication not present in request: '{}'", request.getRemoteAddr());

            modelAndView.addObject(ATTRIBUTE_AUTHENTICATED, false);

            return;
        }


        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails details = (UserDetails) principal;

            modelAndView.addObject(ATTRIBUTE_USERNAME, details.getUsername());
            modelAndView.addObject(ATTRIBUTE_AUTHENTICATED, true);

            log.info("Added user details: '{}'", details);
        } else {
            log.info("User '{}' is an anonymous user", request.getRemoteAddr());

            modelAndView.addObject(ATTRIBUTE_AUTHENTICATED, false);
        }
    }
}
