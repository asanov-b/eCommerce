package com.ecommerce.ecommerce.modules.product.service;

import com.ecommerce.ecommerce.modules.product.dto.ProductResDTO;
import com.ecommerce.ecommerce.modules.product.dto.ProductDTO;
import com.ecommerce.ecommerce.modules.product.dto.ProductSearchDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ProductsService {

    Page<ProductResDTO> getAll(UUID categoryName, Integer page, Integer size);

    ProductResDTO save(ProductDTO productDTO);

    ProductResDTO update(UUID id, ProductDTO productDTO);

    void delete(UUID id);

    ProductResDTO getProduct(UUID id);

    Page<ProductResDTO> search(ProductSearchDTO productSearchDTO);
}
