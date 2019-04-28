package com.github.zukkari.pdffrontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    @GetMapping("/login")
    public String login() {
        return "auth/login.html";
    }

    @GetMapping("/signup")
    public String register() {
        return "auth/register.html";
    }
}
