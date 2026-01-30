package com.ecommerce.ecommerce.modules.product.entity;

import com.ecommerce.ecommerce.common.audit.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductOutcome extends BaseEntity {
    @Column(nullable = false)
    private Integer quantity;

    @CreationTimestamp
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
