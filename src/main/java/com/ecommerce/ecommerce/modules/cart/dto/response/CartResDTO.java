package com.ecommerce.ecommerce.modules.cart.dto.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CartResDTO(
        UUID cartId,
        List<CartItemResDTO> items,
        BigDecimal total
) {}