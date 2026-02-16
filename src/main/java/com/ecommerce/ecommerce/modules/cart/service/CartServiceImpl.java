package com.ecommerce.ecommerce.modules.cart.service;

import com.ecommerce.ecommerce.common.exception.CustomException;
import com.ecommerce.ecommerce.modules.cart.dto.request.AddCartItemsDTO;
import com.ecommerce.ecommerce.modules.cart.dto.request.UpdateCartItemQtyDTO;
import com.ecommerce.ecommerce.modules.cart.dto.response.CartResDTO;
import com.ecommerce.ecommerce.modules.cart.entity.Cart;
import com.ecommerce.ecommerce.modules.cart.entity.CartItem;
import com.ecommerce.ecommerce.modules.cart.mapper.CartMapper;
import com.ecommerce.ecommerce.modules.cart.repository.CartItemRepository;
import com.ecommerce.ecommerce.modules.cart.repository.CartRepository;
import com.ecommerce.ecommerce.modules.product.entity.Product;
import com.ecommerce.ecommerce.modules.product.repository.ProductRepository;
import com.ecommerce.ecommerce.modules.user.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final EntityManager entityManager;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    @Transactional
    public CartResDTO getMyCart(UUID userId) {
        Cart cart = getOrCreateCart(userId);
        return cartMapper.toCartResDTO(cart);
    }

    @Override
    @Transactional
    public CartResDTO addItems(AddCartItemsDTO itemsDTO, UUID userId) {
        Cart cart = getOrCreateCart(userId);
        Product product = productRepository.findById(itemsDTO.productId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Product not found"));

        Optional<CartItem> cartItem = cartItemRepository.findByCart_IdAndProduct_Id(cart.getId(), product.getId());
        if (cartItem.isPresent()) {
            cartItem.get().setQuantity(cartItem.get().getQuantity() + itemsDTO.quantity());
            log.info("Added cartItem cartItemId={} cartId={}", itemsDTO.productId(), cart.getId());
        } else {
            cart.addItem(new CartItem(null, product, itemsDTO.quantity()));
            log.info("Created cartItem cartItemId={} cartId={}", itemsDTO.productId(), cart.getId());
        }
        entityManager.flush();
        return cartMapper.toCartResDTO(cart);
    }

    private Cart getOrCreateCart(UUID userId) {
        return cartRepository.getCartByUserId(userId)
                .orElseGet(() -> {
                    Cart save = cartRepository.save(new Cart(entityManager.getReference(User.class, userId)));
                    log.info("Created new cart id={}", save.getId());
                    return save;
                });
    }

    @Override
    @Transactional
    public CartResDTO updateItemQTY(UUID itemId, UpdateCartItemQtyDTO qtyDTO, UUID userId) {
        CartItem cartItem = cartItemRepository.findByIdAndCart_User_Id(itemId, userId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Cart item not found"));

        cartItem.setQuantity(qtyDTO.quantity());
        log.info("Updated cartItem id={} qty={} ", cartItem.getId(), qtyDTO.quantity());

        return cartMapper.toCartResDTO(cartItem.getCart());
    }

    @Override
    @Transactional
    public void removeItem(UUID itemId, UUID userId) {
        CartItem cartItem = cartItemRepository.findByIdAndCart_User_Id(itemId, userId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Cart item not found"));

        cartItemRepository.delete(cartItem);
        log.info("Removed cartItem id={}", itemId);
    }

    @Override
    @Transactional
    public void clearCart(UUID userId) {
        Cart cart = cartRepository.getCartByUserId(userId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Cart not found"));
        cart.getItems().clear();
        log.info("Cleared cart id={}", cart.getId());
    }
}
