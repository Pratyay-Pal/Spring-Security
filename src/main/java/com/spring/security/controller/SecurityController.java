package com.spring.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SecurityController {

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome";
    }

    @GetMapping("/secured")
    public String secured() {
        return "Secured Endpoint";
    }
}
