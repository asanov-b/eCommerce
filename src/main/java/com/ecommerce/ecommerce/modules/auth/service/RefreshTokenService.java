package com.ecommerce.ecommerce.modules.auth.service;

import com.ecommerce.ecommerce.modules.auth.dto.TokenDTO;
import com.ecommerce.ecommerce.modules.user.entity.User;
import org.springframework.security.access.prepost.PreAuthorize;

public interface RefreshTokenService {

    String generateRefreshToken();

    TokenDTO issueTokens(User user);

    TokenDTO rotateTokens(String oldRefreshToken);

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    void revoke(String refreshToken);
}
