package com.ecommerce.ecommerce.modules.order.dto.request;

import com.ecommerce.ecommerce.modules.order.entity.enums.OrderStatus;

public record UpdateOrderStatusDTO(OrderStatus status) {}
