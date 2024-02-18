package com.spring.security.filter;

import com.spring.security.constants.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class JWTTokenGeneratorFilter extends OncePerRequestFilter {

    Logger logger = LogManager.getLogger(JWTTokenGeneratorFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("In JWTTokenGeneratorFilter");
        if (authentication != null) {
            SecretKey secretKey = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
            String jwt = Jwts.builder().setIssuer("Backend JWTTokenGeneratorFilter ").setSubject("JWT TOKEN")
                    .claim("username", authentication.getName())
                    .claim("authorities", listToString(authentication.getAuthorities()))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date((new Date()).getTime() + 60000000))
                    .signWith(secretKey)
                    .compact();
            response.setHeader("Authorization", jwt);
            logger.info("TOKEN GENERATED : " + jwt);
        } else
            throw new BadCredentialsException("Invalid Password");
        filterChain.doFilter(request, response);
    }

    protected String listToString(Collection<? extends GrantedAuthority> authList) {
        Set<String> auth = new HashSet<>();
        for (GrantedAuthority authority : authList) {
            auth.add(authority.getAuthority());
        }
        return String.join(",", auth);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/login");
    }
}
