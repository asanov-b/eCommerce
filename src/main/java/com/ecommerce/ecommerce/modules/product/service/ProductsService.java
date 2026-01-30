package com.ecommerce.ecommerce.modules.product.service;

import com.ecommerce.ecommerce.modules.product.dto.ProductResDTO;
import com.ecommerce.ecommerce.modules.product.dto.ProductDTO;
import com.ecommerce.ecommerce.modules.product.dto.ProductSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.UUID;

public interface ProductsService {

    Page<ProductResDTO> getAll(UUID categoryName, Integer page, Integer size);

    @PreAuthorize("hasRole('ADMIN')")
    ProductResDTO save(ProductDTO productDTO);

    @PreAuthorize("hasRole('ADMIN')")
    ProductResDTO update(UUID id, ProductDTO productDTO);

    @PreAuthorize("hasRole('ADMIN')")
    void delete(UUID id);

    ProductResDTO getProduct(UUID id);

    Page<ProductResDTO> search(ProductSearchDTO productSearchDTO);
}
