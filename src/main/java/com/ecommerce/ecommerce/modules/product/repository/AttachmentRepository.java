package com.ecommerce.ecommerce.modules.product.repository;

import com.ecommerce.ecommerce.modules.product.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {
}