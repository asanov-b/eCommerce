package com.ecommerce.ecommerce.modules.product.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface FileService {

    List<UUID> save(List<MultipartFile> multipartFile);
}
