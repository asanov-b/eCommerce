package com.ecommerce.ecommerce.modules.auth.dto;

import com.ecommerce.ecommerce.modules.user.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

/**
 * DTO for {@link User}
 */
public record LoginDTO(
        @Email
        String email,
        @Size(min = 8)
        String password
) {
}