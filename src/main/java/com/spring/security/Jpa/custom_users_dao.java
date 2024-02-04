package com.spring.security.Jpa;

import com.spring.security.Entity.custom_users;

import java.util.List;

public interface custom_users_dao {

    public List<custom_users> getUsersByEmail(String email);
    public void createUser(String user_name,String password,String email,String role);
    public boolean loginSuccess(String user_name,String password);
}
