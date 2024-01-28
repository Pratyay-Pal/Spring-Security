package com.spring.security.Jpa;

import com.spring.security.Controller.SecurityController;
import com.spring.security.Entity.custom_users;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class custom_users_dao_impl implements custom_users_dao {

    Logger logger = LogManager.getLogger(custom_users_dao_impl.class);
    @Autowired
    custom_users_jpa customUsersJpa;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<custom_users> getUsersByEmail(String email) {
        return customUsersJpa.findByEmail(email);
    }

    @Override
    public void createUser(String user_name, String password, String email, String role) {
        String encrypted_password = passwordEncoder.encode(password); //THIS WILL USE THE ENCODING TECHNIQUE DECLARED IN THE WEB SECURITY CONFIG CLASS
        custom_users customUser = new custom_users(user_name, encrypted_password, email, true, role);
        customUsersJpa.save(customUser);
        logger.info(user_name+" created in custom_users table with email ID "+email+" having role "+role+" having encrypted password "+encrypted_password);
    }
}
