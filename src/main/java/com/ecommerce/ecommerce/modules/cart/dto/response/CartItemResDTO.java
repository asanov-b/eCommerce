package com.ecommerce.ecommerce.modules.cart.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record CartItemResDTO(
        UUID itemId,
        UUID productId,
        String productName,
        BigDecimal unitPrice,
        Integer quantity,
        BigDecimal lineTotal
) {}