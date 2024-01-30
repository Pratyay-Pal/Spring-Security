package com.spring.security.Config;

import com.spring.security.Entity.custom_users;
import com.spring.security.Jpa.custom_users_dao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    custom_users_dao customUsersDao;
    @Autowired
    PasswordEncoder passwordEncoder;
    Logger logger = LogManager.getLogger(CustomAuthenticationProvider.class);

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        logger.info("Inside Custom Authentication Provider method");
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        List<custom_users> customUsers = customUsersDao.getUsersByEmail(authentication.getName());
        if (customUsers.isEmpty())
            throw new UsernameNotFoundException("No Username found by email : " + username);
        else{
            if(passwordEncoder.matches(password, customUsers.get(0).getPassword())){
                logger.info("Password Match. Adding Authorities.");
                List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
                grantedAuthorities.add(new SimpleGrantedAuthority(customUsers.get(0).getRole()));
                // THIS IS NECESSARY TO BE CREATED. THIS OBJECT SIGNIFIES SUCCESSFUL AUTHENTICATION. ONLY AFTER ADDING CUSTOM LOGIC, CREATE THIS OBJECT
                // PASSWORD WILL BE ERASED INTERNALLY. AUTHENTICATED = TRUE WILL BE ADDED TO THIS OBJECT
                return new UsernamePasswordAuthenticationToken(username, password, grantedAuthorities);
            } else
                throw new BadCredentialsException("Invalid Password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
