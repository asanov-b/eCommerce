package com.ecommerce.ecommerce.modules.order.repository;

import com.ecommerce.ecommerce.modules.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderItemsRepository extends JpaRepository<OrderItem, UUID> {
}