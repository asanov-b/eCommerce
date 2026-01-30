package com.ecommerce.ecommerce.modules.auth.dto;

import com.ecommerce.ecommerce.modules.auth.entity.RefreshToken;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO for {@link RefreshToken}
 */
public record RefreshRequestDTO(
        @NotBlank String token
) {
}