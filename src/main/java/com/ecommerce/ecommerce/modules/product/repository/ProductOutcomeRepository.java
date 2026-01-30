package com.ecommerce.ecommerce.modules.product.repository;

import com.ecommerce.ecommerce.modules.product.entity.ProductOutcome;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductOutcomeRepository extends JpaRepository<ProductOutcome, UUID> {
}