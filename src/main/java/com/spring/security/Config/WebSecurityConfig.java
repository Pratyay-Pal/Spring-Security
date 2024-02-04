package com.spring.security.Config;

import com.spring.security.filter.CsrfCookieFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.sql.DataSource;
import java.util.Collections;

@Component
@Configuration
public class WebSecurityConfig {

    Logger logger = LogManager.getLogger(WebSecurityConfig.class);

    @Bean
    @SuppressWarnings("Deprecated")
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        logger.info("Going through securityFilterChain");

        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();
        csrfTokenRequestAttributeHandler.setCsrfRequestAttributeName("_csrf");
        httpSecurity
                .securityContext().requireExplicitSave(false)
                .and()
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .cors().configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration corsConfiguration = new CorsConfiguration();
                        corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:3000/"));
                        corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
                        corsConfiguration.setAllowCredentials(true);
                        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
                        return corsConfiguration;
                    }
                })
                .and()
                .csrf((csrf) -> csrf.csrfTokenRequestHandler(csrfTokenRequestAttributeHandler).ignoringRequestMatchers("/loginSuccess")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/insecure", "/registerNewUser", "/loginSuccess").permitAll()
                                .requestMatchers("/securedByLogin").authenticated()
                )
                .formLogin()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new Http403ForbiddenEntryPoint())
                .and()
                .httpBasic();
        return httpSecurity.build();
    }

    //    @Bean  // UNCOMMENT TO CREATE THE BEAN IF IN MEMORY USER MANAGER IS REQUIRED
    public InMemoryUserDetailsManager userDetailsManager() {
        logger.info("In Memory User Creation in userDetailsManager");
        UserDetails user_read = User.withDefaultPasswordEncoder() //INLINE DECLARATION OF PASSWORD ENCODER
                .username("read")
                .password("password")
                .authorities("read")
                .build();

        UserDetails admin = User.withUsername("admin") //PASSWORD ENCODER BEAN MUST BE CREATED SINCE NO INLINE DECLARATION OF ENCODER
                .password("password")
                .authorities("admin")
                .build();

        UserDetails user_write = User.withUsername("write") //PASSWORD ENCODER BEAN MUST BE CREATED SINCE NO INLINE DECLARATION OF ENCODER
                .password("password")
                .authorities("write")
                .build();

        return new InMemoryUserDetailsManager(user_read, user_write, admin);
    }

    //    @Bean  // UNCOMMENT TO CREATE THE BEAN IF DEFAULT SPRING USER AND AUTHORITIES SCHEMA IS USED
    public UserDetailsService userDetailsService(DataSource dataSource) {
        logger.info("New JDBC DataSource");
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance(); // UNCOMMENT TO USE DEFAULT UNENCRYPTED PASSWORD ENCODER
        return new BCryptPasswordEncoder(); // HASHING PASSWORD ENCODER
    }
}
