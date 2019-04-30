package com.github.zukkari.pdffrontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "auth/login.html";
    }

    @GetMapping("/signup")
    public String register() {
        return "auth/register.html";
    }
}
