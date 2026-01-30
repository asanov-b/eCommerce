package com.ecommerce.ecommerce.modules.auth.service;

import com.ecommerce.ecommerce.common.exception.CustomException;
import com.ecommerce.ecommerce.common.security.JwtTokenService;
import com.ecommerce.ecommerce.modules.auth.dto.TokenDTO;
import com.ecommerce.ecommerce.modules.auth.entity.RefreshToken;
import com.ecommerce.ecommerce.modules.auth.repository.RefreshTokenRepository;
import com.ecommerce.ecommerce.modules.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenService jwtTokenService;

    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    public String generateRefreshToken() {
        byte[] bytes = new byte[48];
        secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    @Override
    public TokenDTO issueTokens(User user) {
        String accessToken = jwtTokenService.generateToken(user);
        String refreshToken = generateRefreshToken();

        RefreshToken refreshTokenEntity = RefreshToken.builder()
                .user(user)
                .createdAt(Instant.now())
                .token(refreshToken)
                .expiresAt(Instant.now().plusMillis(Duration.ofDays(14).toMillis()))
                .revoked(false)
                .build();
        refreshTokenRepository.save(refreshTokenEntity);
        return new TokenDTO(accessToken, refreshToken);
    }

    @Transactional
    @Override
    public TokenDTO rotateTokens(String oldRefreshToken) {

        RefreshToken existing = refreshTokenRepository.findByToken(oldRefreshToken)
                .orElseThrow(() -> {
                    log.warn("Invalid refresh token={}", oldRefreshToken);
                    return new CustomException(HttpStatus.UNAUTHORIZED, "Invalid refresh token");
                });

        if (existing.getRevoked()) {
            log.warn("Refresh token revoked. userId={}", existing.getUser().getId());
            throw new CustomException(HttpStatus.UNAUTHORIZED, "Refresh token revoked");
        }

        if (existing.getExpiresAt().isBefore(Instant.now())) {
            log.warn("Refresh token expired. userId={}", existing.getUser().getId());
            throw new CustomException(HttpStatus.UNAUTHORIZED, "Refresh token expired");
        }

        existing.setRevoked(true);
        refreshTokenRepository.save(existing);

        String refreshToken = generateRefreshToken();
        RefreshToken newRefreshToken = RefreshToken.builder()
                .user(existing.getUser())
                .createdAt(Instant.now())
                .token(refreshToken)
                .expiresAt(existing.getExpiresAt())
                .revoked(false)
                .build();
        refreshTokenRepository.save(newRefreshToken);

        String accessToken = jwtTokenService.generateToken(existing.getUser());
        return new TokenDTO(accessToken, refreshToken);
    }

    @Override
    public void revoke(String refreshToken) {
        RefreshToken refreshToken1 = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> {
                    log.warn("Refresh token not found. refreshToken={}", refreshToken);
                    return new CustomException(HttpStatus.NOT_FOUND, "Refresh token not found");
                });

        refreshToken1.setRevoked(true);
        refreshTokenRepository.save(refreshToken1);
    }
}
