package com.ecommerce.ecommerce.modules.inventory.entity;

import com.ecommerce.ecommerce.common.audit.BaseEntity;
import com.ecommerce.ecommerce.modules.inventory.entity.enums.Reason;
import com.ecommerce.ecommerce.modules.inventory.entity.enums.Type;
import com.ecommerce.ecommerce.modules.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(indexes = {@Index(name = "idx_product_items_product_id", columnList = "product_id")})
public class InventoryTransaction extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Reason reason;

    @Column(nullable = false)
    private Integer quantity;
    private UUID reference_id;
}
