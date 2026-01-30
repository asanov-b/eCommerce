package com.ecommerce.ecommerce.modules.product.repository;

import com.ecommerce.ecommerce.modules.product.entity.ProductIncome;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductIncomeRepository extends JpaRepository<ProductIncome, UUID> {
}