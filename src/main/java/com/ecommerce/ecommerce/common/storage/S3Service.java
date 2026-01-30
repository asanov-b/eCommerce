package com.ecommerce.ecommerce.common.storage;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {

    String uploadFile(MultipartFile file);

}
