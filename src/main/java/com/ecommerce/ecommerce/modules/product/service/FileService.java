package com.ecommerce.ecommerce.modules.product.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface FileService {

    @PreAuthorize("hasRole('ADMIN')")
    List<UUID> save(List<MultipartFile> multipartFile);
}
