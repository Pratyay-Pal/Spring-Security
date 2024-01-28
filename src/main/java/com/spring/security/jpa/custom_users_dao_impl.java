package com.spring.security.jpa;

import com.spring.security.entity.custom_users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class custom_users_dao_impl implements custom_users_dao{

    @Autowired
    custom_users_jpa customUsersJpa;

    @Override
    public List<custom_users> getUsersByEmail(String email) {
        return customUsersJpa.findByEmail(email);
    }
}
