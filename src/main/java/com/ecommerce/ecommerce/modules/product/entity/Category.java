package com.ecommerce.ecommerce.modules.product.entity;

import com.ecommerce.ecommerce.common.audit.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category extends BaseEntity {
    @Column(nullable = false)
    private String name;
}
