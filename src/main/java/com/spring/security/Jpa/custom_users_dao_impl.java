package com.spring.security.Jpa;

import com.spring.security.Entity.custom_users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class custom_users_dao_impl implements custom_users_dao {

    @Autowired
    custom_users_jpa customUsersJpa;

    @Override
    public List<custom_users> getUsersByEmail(String email) {
        return customUsersJpa.findByEmail(email);
    }

    @Override
    public void createUser(String user_name, String password, String email, String role) {
        custom_users customUser = new custom_users(user_name, password, email, true, role);
        customUsersJpa.save(customUser);
    }
}
