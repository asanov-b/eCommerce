package com.ecommerce.ecommerce.modules.product.repository;

import com.ecommerce.ecommerce.modules.product.entity.Category;
import com.ecommerce.ecommerce.modules.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> , JpaSpecificationExecutor<Product> {

    Page<Product> findAllByCategoryId(UUID categoryId, Pageable pageable);

    boolean existsByCategory(Category category);
}