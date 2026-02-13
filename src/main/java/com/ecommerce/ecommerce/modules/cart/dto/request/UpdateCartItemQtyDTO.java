package com.ecommerce.ecommerce.modules.cart.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateCartItemQtyDTO(
        @NotNull
        @Min(1)
        Integer quantity
) {}
