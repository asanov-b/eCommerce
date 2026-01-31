package com.ecommerce.ecommerce.modules.order.dto.response;

import com.ecommerce.ecommerce.modules.order.entity.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderResDTO(
        UUID orderId,
        OrderStatus status,
        BigDecimal total,
        LocalDateTime createdAt,
        List<OrderItemResDTO> items
) {
}
