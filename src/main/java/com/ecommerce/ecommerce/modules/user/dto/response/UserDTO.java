package com.ecommerce.ecommerce.modules.user.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record UserDTO(
        UUID id,
        String email,
        String firstName,
        String lastName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<String> roles
) {}
