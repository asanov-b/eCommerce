package com.ecommerce.ecommerce.common.security;

import com.ecommerce.ecommerce.common.exception.CustomException;
import com.ecommerce.ecommerce.modules.user.entity.User;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class MyFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;

    public MyFilter(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            try {
                if (jwtTokenService.isValid(token)) {
                    User user = jwtTokenService.getUserObject(token);
                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (JwtException e) {
                log.error("Invalid token. path={}", request.getRequestURI());
                throw new CustomException(HttpStatus.UNAUTHORIZED, "Invalid token");
            }
        }
        filterChain.doFilter(request, response);
    }
}
