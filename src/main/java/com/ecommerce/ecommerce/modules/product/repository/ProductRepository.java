package com.ecommerce.ecommerce.modules.product.repository;

import com.ecommerce.ecommerce.modules.product.entity.Category;
import com.ecommerce.ecommerce.modules.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> , JpaSpecificationExecutor<Product> {

    Page<Product> findAllByCategoryId(UUID categoryId, Pageable pageable);

    boolean existsByCategory(Category category);

    @Modifying
    @Query("update Product p set p.leftover = p.leftover + :quantity where p.id = :id")
    int addLeftover(UUID id, Integer quantity);

    @Modifying
    @Query("update Product p set p.leftover = p.leftover - :quantity where p.id = :id and p.leftover >= :quantity")
    int subtractLeftover(UUID id, Integer quantity);
}