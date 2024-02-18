package com.spring.security.filter;

import com.spring.security.constants.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JWTTokenValidatorFilter extends OncePerRequestFilter {

    Logger logger = LogManager.getLogger(JWTTokenValidatorFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("In JWTTokenValidatorFilter");
        String jwt = request.getHeader("Authorization");
        logger.info("TOKEN RECEIVED : " + jwt);
        if (jwt != null) {
            try {
                SecretKey secretKey = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
                Claims claims = Jwts.parser()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();
                String username = String.valueOf(claims.get("username"));
                String authorities = String.valueOf(claims.get("authorities"));
                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                throw new BadCredentialsException("Invalid JWT Token Received");
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/securedByLogin");
    }
}
