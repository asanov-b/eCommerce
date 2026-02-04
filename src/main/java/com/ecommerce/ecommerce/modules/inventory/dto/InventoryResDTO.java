package com.ecommerce.ecommerce.modules.inventory.dto;

import com.ecommerce.ecommerce.modules.inventory.entity.enums.Reason;
import com.ecommerce.ecommerce.modules.inventory.entity.enums.Type;

import java.time.LocalDateTime;
import java.util.UUID;

public record InventoryResDTO(
        UUID id,
        UUID productId,
        Type type,
        Reason reason,
        Integer lastTransactionCount,
        Integer currentLeftover,
        UUID referenceId,
        LocalDateTime createdAt
) {
}
