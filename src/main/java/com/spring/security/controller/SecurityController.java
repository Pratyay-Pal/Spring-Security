package com.spring.security.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SecurityController {

    Logger logger = LogManager.getLogger(SecurityController.class);

    @GetMapping("/welcome")
    public String welcome() {
        logger.info("/welcome received");
        return "Welcome";
    }

    @GetMapping("/secured")
    public String secured() {
        logger.info("/secured received");
        return "Secured Endpoint";
    }
}
