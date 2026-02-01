package com.ecommerce.ecommerce.modules.inventory.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record LeftoverResDTO(
        UUID productId,
        Integer leftover,
        LocalDateTime lastIncomeAt
) {
}
