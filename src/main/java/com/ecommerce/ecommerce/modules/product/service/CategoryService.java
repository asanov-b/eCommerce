package com.ecommerce.ecommerce.modules.product.service;

import com.ecommerce.ecommerce.modules.product.dto.CategoryDTO;
import com.ecommerce.ecommerce.modules.product.dto.CategoryResDTO;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    List<CategoryResDTO> getAllCategory();

    CategoryResDTO getCategory(UUID id);

    CategoryResDTO save(CategoryDTO category);

    void update(UUID id, CategoryDTO categoryDTO);

    void delete(UUID id);

}
