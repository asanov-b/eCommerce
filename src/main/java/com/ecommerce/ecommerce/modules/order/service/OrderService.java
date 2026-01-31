package com.ecommerce.ecommerce.modules.order.service;

import com.ecommerce.ecommerce.modules.order.dto.request.CreateOrderItemsDTO;
import com.ecommerce.ecommerce.modules.order.dto.request.UpdateOrderStatusDTO;
import com.ecommerce.ecommerce.modules.order.dto.response.OrderResDTO;
import com.ecommerce.ecommerce.modules.order.entity.enums.OrderStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    @Transactional
    @PreAuthorize("hasRole('USER')")
    OrderResDTO create(List<CreateOrderItemsDTO> orderItemsDTO, UUID principal);

    @PreAuthorize("hasRole('USER')")
    Page<OrderResDTO> getMyOrders(Integer page, Integer size, OrderStatus orderStatus, UUID id);

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    OrderResDTO getOrder(UUID orderId, UUID id);

    @PreAuthorize("hasRole('ADMIN')")
    Page<OrderResDTO> getOrders(Integer page, Integer size, OrderStatus status, UUID userId);

    @PreAuthorize("hasRole('ADMIN')")
    OrderResDTO updateOrderStatus(UUID orderId, UpdateOrderStatusDTO updateOrderStatusDTO);
}
