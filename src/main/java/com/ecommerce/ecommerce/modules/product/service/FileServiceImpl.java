package com.ecommerce.ecommerce.modules.product.service;

import com.ecommerce.ecommerce.common.exception.CustomException;
import com.ecommerce.ecommerce.modules.product.entity.Attachment;
import com.ecommerce.ecommerce.modules.product.repository.AttachmentRepository;
import com.ecommerce.ecommerce.common.storage.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final S3Service s3Service;
    private final AttachmentRepository attachmentRepository;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.region}")
    private String region;

    @Override
    public List<UUID> save(List<MultipartFile> multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Multipart file is empty");
        }

        List<Attachment> attachments = new ArrayList<>();
        multipartFile.forEach(file -> {
            String imgUrl = "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + s3Service.uploadFile(file);
            Attachment attachment = new Attachment();
            attachment.setImgUrl(imgUrl);
            attachments.add(attachment);
        });
        List<UUID> uuids = attachmentRepository.saveAll(attachments).stream().map(Attachment::getId).toList();
        log.info("Attachments saved. count={}, ids={}", uuids.size(), uuids.stream().limit(10).toList());
        return uuids;
    }
}
