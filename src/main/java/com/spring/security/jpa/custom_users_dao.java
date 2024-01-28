package com.spring.security.jpa;

import com.spring.security.entity.custom_users;
import org.springframework.stereotype.Component;

import java.util.List;

public interface custom_users_dao {

    public List<custom_users> getUsersByEmail(String email);

}
