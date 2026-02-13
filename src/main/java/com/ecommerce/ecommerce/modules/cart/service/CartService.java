package com.ecommerce.ecommerce.modules.cart.service;

import com.ecommerce.ecommerce.modules.cart.dto.request.AddCartItemsDTO;
import com.ecommerce.ecommerce.modules.cart.dto.request.UpdateCartItemQtyDTO;
import com.ecommerce.ecommerce.modules.cart.dto.response.CartResDTO;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.UUID;

public interface CartService {

    @PreAuthorize("hasRole('USER')")
    CartResDTO getMyCart(UUID userId);

    @PreAuthorize("hasRole('USER')")
    CartResDTO addItems(AddCartItemsDTO itemsDTO, UUID userId);

    @PreAuthorize("hasRole('USER')")
    CartResDTO updateItemQTY(UUID itemId, UpdateCartItemQtyDTO qtyDTO, UUID userId);

    @PreAuthorize("hasRole('USER')")
    void removeItem(UUID itemId, UUID userId);

    @PreAuthorize("hasRole('USER')")
    void clearCart(UUID userId);
}
