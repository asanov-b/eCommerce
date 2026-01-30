package com.ecommerce.ecommerce.modules.auth.dto;

import com.ecommerce.ecommerce.modules.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;

import java.util.Objects;

/**
 * DTO for {@link User}
 */

public record RegisterDTO(
        @Email
        @NotBlank
        String email,
        @NotBlank
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s]).{8,64}$",
                message = "Password must be 8-64 chars and include upper, lower, number, and special character")
        String password,
        @NotBlank
        String confirmPassword,
        @Size(min = 2, max = 50)
        String firstName,
        @Size(min = 2, max = 50)
        String lastName
) {
    @JsonIgnore
    @AssertTrue(message = "Password and confirm password must match")
    boolean isPasswordValid() {
        return Objects.equals(password, confirmPassword);
    }
}
