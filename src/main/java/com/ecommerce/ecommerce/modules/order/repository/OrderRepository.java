package com.ecommerce.ecommerce.modules.order.repository;

import com.ecommerce.ecommerce.modules.order.dto.response.OrderResDTO;
import com.ecommerce.ecommerce.modules.order.entity.Order;
import com.ecommerce.ecommerce.modules.order.entity.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID>, JpaSpecificationExecutor<Order> {
    Page<Order> findAllByUserId(UUID userId, Pageable pageable);

    Page<Order> findAllByOrderStatusAndUserId(OrderStatus orderStatus, UUID userId, Pageable pageable);
}