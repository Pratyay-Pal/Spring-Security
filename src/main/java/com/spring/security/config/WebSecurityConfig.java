package com.spring.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@Configuration
public class WebSecurityConfig {

    @Bean
    @SuppressWarnings("Deprecated")
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/secured").authenticated()
                                .requestMatchers("/welcome").permitAll()
                )
                .formLogin();
        return httpSecurity.build();
    }

//    @Bean  // UNCOMMENT TO CREATE THE BEAN IF IN MEMORY USER MANAGER IS REQUIRED
    public InMemoryUserDetailsManager userDetailsManager(){
        UserDetails user_read = User.withDefaultPasswordEncoder() 
                .username("read")
                .password("password")
                .authorities("read")
                .build();

        UserDetails admin = User.withUsername("admin") //PASSWORD ENCODER BEAN MUST BE CREATED
                .password("password")
                .authorities("admin")
                .build();

        UserDetails user_write = User.withUsername("write") //PASSWORD ENCODER BEAN MUST BE CREATED
                .password("password")
                .authorities("write")
                .build();

        return new InMemoryUserDetailsManager(user_read,user_write,admin);
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
