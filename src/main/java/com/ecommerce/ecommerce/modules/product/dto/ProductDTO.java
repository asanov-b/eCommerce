package com.ecommerce.ecommerce.modules.product.dto;

import com.ecommerce.ecommerce.modules.product.validation.OnCreate;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ProductDTO(
        @NotBlank
        String name,
        @NotBlank
        @Size(max = 120)
        String description,
        @NotNull
        @DecimalMin(value = "0.0")
        BigDecimal price,
        @NotEmpty(groups = OnCreate.class)
        List<UUID> attachmentId,
        @NotNull(groups = OnCreate.class)
        UUID categoryId
) {
}
