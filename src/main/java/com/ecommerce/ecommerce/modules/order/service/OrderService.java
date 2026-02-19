package com.ecommerce.ecommerce.modules.order.service;

import com.ecommerce.ecommerce.modules.order.dto.request.UpdateOrderStatusDTO;
import com.ecommerce.ecommerce.modules.order.dto.response.OrderResDTO;
import com.ecommerce.ecommerce.modules.order.entity.enums.OrderStatus;
import com.ecommerce.ecommerce.modules.user.entity.Role;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    @Transactional
    OrderResDTO create(UUID principal);

    Page<OrderResDTO> getMyOrders(Integer page, Integer size, OrderStatus orderStatus, UUID id);

    OrderResDTO getOrder(UUID orderId, UUID id, List<Role> roles);

    Page<OrderResDTO> getOrders(Integer page, Integer size, OrderStatus status, UUID userId);

    OrderResDTO updateOrderStatus(UUID orderId, UpdateOrderStatusDTO updateOrderStatusDTO);
}
