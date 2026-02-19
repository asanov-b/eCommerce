package com.ecommerce.ecommerce.modules.cart.controller;

import com.ecommerce.ecommerce.modules.cart.dto.request.AddCartItemsDTO;
import com.ecommerce.ecommerce.modules.cart.dto.request.UpdateCartItemQtyDTO;
import com.ecommerce.ecommerce.modules.cart.dto.response.CartResDTO;
import com.ecommerce.ecommerce.modules.cart.service.CartService;
import com.ecommerce.ecommerce.modules.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.ecommerce.ecommerce.common.utils.AppPaths.API;
import static com.ecommerce.ecommerce.common.utils.AppPaths.VERSION;

@RestController
@RequiredArgsConstructor
@RequestMapping(API + VERSION + "/cart")
public class CartController {

    private final CartService cartService;

    @PreAuthorize("hasAuthority('CART_READ_OWN')")
    @GetMapping
    public ResponseEntity<CartResDTO> getCart(@AuthenticationPrincipal User userPrincipal) {
        CartResDTO cartResDTO = cartService.getMyCart(userPrincipal.getId());
        return ResponseEntity.ok(cartResDTO);
    }

    @PreAuthorize("hasAuthority('CART_CREATE_OWN')")
    @PostMapping
    public ResponseEntity<CartResDTO> addItems(@Valid @RequestBody AddCartItemsDTO itemsDTO, @AuthenticationPrincipal User userPrincipal) {
        CartResDTO cartResDTO = cartService.addItems(itemsDTO, userPrincipal.getId());
        return ResponseEntity.ok(cartResDTO);
    }

    @PreAuthorize("hasAuthority('CART_UPDATE_OWN')")
    @PutMapping("/items/{itemId}")
    public ResponseEntity<CartResDTO> updateItemQTY(@PathVariable UUID itemId, @Valid @RequestBody UpdateCartItemQtyDTO qtyDTO, @AuthenticationPrincipal User userPrincipal) {
        CartResDTO cartResDTO = cartService.updateItemQTY(itemId, qtyDTO, userPrincipal.getId());
        return ResponseEntity.ok(cartResDTO);
    }

    @PreAuthorize("hasAuthority('CART_DELETE_OWN')")
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<?> deleteItem(@PathVariable UUID itemId, @AuthenticationPrincipal User userPrincipal) {
        cartService.removeItem(itemId, userPrincipal.getId());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('CART_DELETE_OWN')")
    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart(@AuthenticationPrincipal User userPrincipal) {
        cartService.clearCart(userPrincipal.getId());
        return ResponseEntity.noContent().build();
    }
}
