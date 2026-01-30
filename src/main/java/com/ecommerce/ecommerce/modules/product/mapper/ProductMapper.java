package com.ecommerce.ecommerce.modules.product.mapper;

import com.ecommerce.ecommerce.modules.product.dto.ProductResDTO;
import com.ecommerce.ecommerce.modules.product.entity.Attachment;
import com.ecommerce.ecommerce.modules.product.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductResDTO toProductResDTO(Product product) {

        return new ProductResDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getAttachments().stream().map(Attachment::getImgUrl).toList(),
                product.getCategory().getId()
        );
    }
}
