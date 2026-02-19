package com.ecommerce.ecommerce.modules.auth.service;

import com.ecommerce.ecommerce.modules.auth.dto.TokenDTO;
import com.ecommerce.ecommerce.modules.user.entity.User;

public interface RefreshTokenService {

    String generateRefreshToken();

    TokenDTO issueTokens(User user);

    TokenDTO rotateTokens(String oldRefreshToken);

    void revoke(String refreshToken);
}
