package com.ecommerce.ecommerce.modules.product.entity;

import com.ecommerce.ecommerce.common.audit.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Attachment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
    @Column(nullable = false)
    private String imgUrl;
}
