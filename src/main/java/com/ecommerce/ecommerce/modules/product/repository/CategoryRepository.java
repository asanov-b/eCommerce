package com.ecommerce.ecommerce.modules.product.repository;

import com.ecommerce.ecommerce.modules.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    boolean existsCategoryByName(String name);
}