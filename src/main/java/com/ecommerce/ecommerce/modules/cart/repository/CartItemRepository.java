package com.ecommerce.ecommerce.modules.cart.repository;

import com.ecommerce.ecommerce.modules.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    Optional<CartItem> findByCart_IdAndProduct_Id(UUID cartId, UUID productId);

    Optional<CartItem> findByIdAndCart_User_Id(UUID id, UUID cartUserId);
}