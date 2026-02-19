package com.ecommerce.ecommerce.modules.cart.service;

import com.ecommerce.ecommerce.modules.cart.dto.request.AddCartItemsDTO;
import com.ecommerce.ecommerce.modules.cart.dto.request.UpdateCartItemQtyDTO;
import com.ecommerce.ecommerce.modules.cart.dto.response.CartResDTO;

import java.util.UUID;

public interface CartService {

    CartResDTO getMyCart(UUID userId);

    CartResDTO addItems(AddCartItemsDTO itemsDTO, UUID userId);

    CartResDTO updateItemQTY(UUID itemId, UpdateCartItemQtyDTO qtyDTO, UUID userId);

    void removeItem(UUID itemId, UUID userId);

    void clearCart(UUID userId);
}
