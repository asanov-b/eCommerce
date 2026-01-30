package com.ecommerce.ecommerce.modules.product.service;

import com.ecommerce.ecommerce.modules.product.dto.CategoryDTO;
import com.ecommerce.ecommerce.modules.product.dto.CategoryResDTO;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    List<CategoryResDTO> getAllCategory();

    CategoryResDTO getCategory(UUID id);

    @PreAuthorize("hasRole('ADMIN')")
    CategoryResDTO save(CategoryDTO category);

    @PreAuthorize("hasRole('ADMIN')")
    void update(UUID id, CategoryDTO categoryDTO);

    @PreAuthorize("hasRole('ADMIN')")
    void delete(UUID id);

}
