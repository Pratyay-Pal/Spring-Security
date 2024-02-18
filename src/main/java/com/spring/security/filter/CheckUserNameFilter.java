package com.spring.security.filter;

import com.mysql.cj.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CheckUserNameFilter extends OncePerRequestFilter {

    Logger logger = LogManager.getLogger(CheckUserNameFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = ((HttpServletRequest) request).getHeader("AUTHORIZATION");
        logger.info("INCOMING AUTH HEADER : " + authHeader);
        if (authHeader != null) {
            authHeader = authHeader.trim();
            if (StringUtils.startsWithIgnoreCase(authHeader, "Basic")) {
                byte[] encodedToken = authHeader.substring(6).getBytes(StandardCharsets.UTF_8);
                byte[] decodedToken;
                try {
                    decodedToken = Base64.getDecoder().decode(encodedToken);
                    String token = new String(decodedToken);
                    logger.info("Decoded Token : " + token);
                    if (!token.contains(":"))
                        throw new BadCredentialsException("Invalid Basic Auth Header");
                    String[] credentials = token.split(":");
                    if (credentials[0].equals("InvalidUser")) {
                        logger.info("InvalidUser username found");
                        ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        return;
                    }
                } catch (IllegalArgumentException illegalArgumentException) {
                    throw new BadCredentialsException("Unable to parse Basic Auth Header");
                }
            }
        }
        filterChain.doFilter((HttpServletRequest) request, (HttpServletResponse) response);
    }
}
