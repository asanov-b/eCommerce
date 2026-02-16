package com.ecommerce.ecommerce.modules.user.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.Objects;

public record ChangePasswordDTO(
        @NotBlank
        String currentPassword,

        @NotBlank
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s]).{8,64}$",
                message = "Password must be 8-64 chars and include upper, lower, number, and special character")
        String newPassword,

        @NotBlank
        String confirmPassword
) {
    @JsonIgnore
    @AssertTrue(message = "Password and confirm password must match")
    boolean isPasswordValid() {
        return Objects.equals(newPassword, confirmPassword);
    }
}