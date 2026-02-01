package com.ecommerce.ecommerce.modules.inventory.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record InventoryInDTO(
        @NotNull
        UUID productId,
        @NotNull
        @Min(1)
        Integer quantity,
        String note
) {
}
