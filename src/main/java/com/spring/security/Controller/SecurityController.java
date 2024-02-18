package com.spring.security.Controller;

import com.spring.security.Jpa.custom_users_dao;
import com.spring.security.RequestResponse.UserRegistration.UserRegistrationRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    Logger logger = LogManager.getLogger(SecurityController.class);
    @Autowired
    custom_users_dao customUsersDao;

    @GetMapping("/insecure")
    public String welcome() {
        logger.info("/insecure received");
        return "This endpoint is insecure. Can be accessed without login.";
    }

    @GetMapping("/securedByLogin")
    public String securedByLogin() {
        logger.info("/securedByLogin received");
        return "This Endpoint is Secured by Login only. If you can see this, the credentials are correct.";
    }

    @GetMapping("/login")
    public String login() {
        logger.info("/login received");
        return "If you can see this, the credentials are correct. You have received a JWT Token. Use it to access restricted endpoints.";
    }

    @PostMapping("/registerNewUser")
    public void register(@RequestBody UserRegistrationRequest userRegistrationRequest) {
        logger.info("/registerNewUser received with user details : " + userRegistrationRequest.toString());
        customUsersDao.createUser(
                userRegistrationRequest.getUser_name(),
                userRegistrationRequest.getPassword(),
                userRegistrationRequest.getEmail(),
                userRegistrationRequest.getRole()
        );
    }
}
