package com.ecommerce.ecommerce.common.security;


import com.ecommerce.ecommerce.common.exception.CustomException;
import com.ecommerce.ecommerce.modules.user.entity.Role;
import com.ecommerce.ecommerce.modules.user.entity.RoleName;
import com.ecommerce.ecommerce.modules.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenService {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    public String generateToken(User user) {
        String compact = Jwts.builder()
                .subject(user.getUsername())
                .claim("id", user.getId().toString())
                .claim("roles", user.getRoles().stream().map(role -> role.getRole().name()).collect(Collectors.joining(",")))
                .issuedAt(new Date())
                .expiration(Date.from(Instant.now().plus(Duration.ofMinutes(15))))
                .signWith(getSecretKey())
                .compact();
        log.info("Generated token for userId={}", user.getId());
        return compact;
    }

    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public boolean isValid(String token) {
        try {
            Jwts
                    .parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("Token expired");
            throw new CustomException(HttpStatus.UNAUTHORIZED, "Token expired send refresh token or login again");
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public User getUserObject(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        String id = claims.get("id", String.class);
        String email = claims.getSubject();
        String roles = claims.get("roles", String.class);
        List<Role> authorities = Arrays.stream(roles.split(",")).map(r -> new Role(RoleName.valueOf(r))).toList();
        User user = new User(email, authorities);
        user.setId(UUID.fromString(id));
        return user;
    }
}
