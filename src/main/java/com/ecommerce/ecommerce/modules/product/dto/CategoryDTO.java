package com.ecommerce.ecommerce.modules.product.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryDTO(
        @NotBlank String name
) {
}
