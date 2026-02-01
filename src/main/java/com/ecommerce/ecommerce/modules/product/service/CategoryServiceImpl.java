package com.ecommerce.ecommerce.modules.product.service;

import com.ecommerce.ecommerce.common.exception.CustomException;
import com.ecommerce.ecommerce.modules.product.dto.CategoryDTO;
import com.ecommerce.ecommerce.modules.product.dto.CategoryResDTO;
import com.ecommerce.ecommerce.modules.product.entity.Category;
import com.ecommerce.ecommerce.modules.product.repository.CategoryRepository;
import com.ecommerce.ecommerce.modules.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public List<CategoryResDTO> getAllCategory() {
        return categoryRepository.findAll().stream()
                .map(category -> new CategoryResDTO(category.getId(), category.getName()))
                .toList();
    }

    @Override
    public CategoryResDTO getCategory(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Category not found. id= " + id));
        return new CategoryResDTO(category.getId(), category.getName());
    }

    @Override
    public CategoryResDTO save(CategoryDTO category) {
        if (categoryRepository.existsCategoryByName(category.name())) {
            throw new CustomException(HttpStatus.CONFLICT, "Category with name already exists");
        }
        log.info("Saved category={}", category.name());
        Category saved = categoryRepository.save(new Category(category.name()));
        return new CategoryResDTO(saved.getId(), saved.getName());
    }

    @Override
    public void update(UUID id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> {
            return new CustomException(HttpStatus.NOT_FOUND, "Category not found. id= " + id);
        });
        category.setName(categoryDTO.name());
        categoryRepository.save(category);
        log.info("Updated category successful id={}", category.getId());
    }

    @Override
    public void delete(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->  new CustomException(HttpStatus.NOT_FOUND, "Category not found. id= " + id));

        if (productRepository.existsByCategory(category)) {
            throw new CustomException(HttpStatus.CONFLICT, "Category not empty");
        }
        categoryRepository.delete(category);
        log.info("Deleted category successful id={}", category.getId());
    }
}
