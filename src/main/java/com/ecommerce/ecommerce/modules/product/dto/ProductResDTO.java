package com.ecommerce.ecommerce.modules.product.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ProductResDTO(
        UUID id,
        String name,
        String description,
        BigDecimal price,
        Integer leftOver,
        List<String> attachmentImgUrls,
        UUID categoryId
) {
}
