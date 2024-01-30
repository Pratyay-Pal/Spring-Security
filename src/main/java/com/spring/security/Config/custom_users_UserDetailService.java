package com.spring.security.Config;

import com.spring.security.Entity.custom_users;
import com.spring.security.Jpa.custom_users_dao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class custom_users_UserDetailService implements UserDetailsService {

    Logger logger = LogManager.getLogger(custom_users_UserDetailService.class);

    @Autowired
    custom_users_dao customUsersDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("In custom loadUserByUsername class to find the user by the username = " + username);
        String userName, password = null;
        boolean enabled = false;
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        List<custom_users> customUsers = customUsersDao.getUsersByEmail(username);
        if (customUsers.isEmpty())
            throw new UsernameNotFoundException("No Username found by email : " + username);
        else {
            logger.info("Customer found with details = "+customUsers.get(0).toString());
            userName = customUsers.get(0).getUser_name();
            password = customUsers.get(0).getPassword();
            enabled = customUsers.get(0).isEnabled();
            grantedAuthorities.add(new SimpleGrantedAuthority(customUsers.get(0).getRole()));
        }
        //CREATING USER FOR USER DETAILS SERVICE
        logger.info("Creating user");
        return new User(userName, password, enabled, true, true, true, grantedAuthorities);
    }
}
