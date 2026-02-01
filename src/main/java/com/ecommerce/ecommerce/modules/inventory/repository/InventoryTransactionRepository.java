package com.ecommerce.ecommerce.modules.inventory.repository;

import com.ecommerce.ecommerce.modules.inventory.entity.InventoryTransaction;
import com.ecommerce.ecommerce.modules.inventory.entity.enums.Type;
import com.ecommerce.ecommerce.modules.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface InventoryTransactionRepository extends JpaRepository<InventoryTransaction, UUID>, JpaSpecificationExecutor<InventoryTransaction> {

    Optional<InventoryTransaction> findFirstByProductAndTypeOrderByCreatedAtDesc(Product product, Type type);
}