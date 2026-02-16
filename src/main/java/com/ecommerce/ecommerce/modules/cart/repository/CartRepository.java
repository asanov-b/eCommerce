package com.ecommerce.ecommerce.modules.cart.repository;

import com.ecommerce.ecommerce.modules.cart.entity.Cart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
    @EntityGraph(attributePaths = {"items", "items.product"})
    Optional<Cart> getCartByUserId(UUID userId);
}