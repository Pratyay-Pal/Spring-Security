package com.spring.security.Controller;

import com.spring.security.Jpa.custom_users_dao;
import com.spring.security.RequestResponse.UserRegistration.UserRegistrationRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
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
        return "This Endpoint is Secured by Login only";
    }

    @PostMapping("/registerNewUser")
    public HttpStatus register(@RequestBody UserRegistrationRequest userRegistrationRequest) {
        logger.info("/registerNewUser received with user details : "+userRegistrationRequest.toString());
        customUsersDao.createUser(
                userRegistrationRequest.getUser_name(),
                userRegistrationRequest.getPassword(),
                userRegistrationRequest.getEmail(),
                userRegistrationRequest.getRole()
        );
        return HttpStatus.OK;
    }
}
