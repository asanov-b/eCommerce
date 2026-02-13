package com.ecommerce.ecommerce.modules.cart.mapper;

import com.ecommerce.ecommerce.modules.cart.dto.response.CartItemResDTO;
import com.ecommerce.ecommerce.modules.cart.dto.response.CartResDTO;
import com.ecommerce.ecommerce.modules.cart.entity.Cart;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class CartMapper {
    public CartResDTO toCartResDTO(Cart cart) {

        List<CartItemResDTO> itemResDTOs = cart.getItems().stream()
                .map(item -> {
                    Integer quantity = item.getQuantity();
                    BigDecimal price = item.getProduct().getPrice();

                    return new CartItemResDTO(
                            item.getId(),
                            item.getProduct().getId(),
                            item.getProduct().getName(),
                            price,
                            quantity,
                            price.multiply(BigDecimal.valueOf(quantity))
                    );
                })
                .toList();

        BigDecimal total = itemResDTOs.stream()
                .map(CartItemResDTO::lineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CartResDTO(cart.getId(), itemResDTOs, total);
    }
}
