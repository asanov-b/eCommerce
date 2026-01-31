package com.ecommerce.ecommerce.modules.order.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemResDTO(
        UUID productId,
        String productName,
        BigDecimal unitPrice,
        Integer quantity,
        BigDecimal lineTotal
) {
}
