package com.ecommerce.ecommerce.common.storage;

import com.ecommerce.ecommerce.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Override
    public String uploadFile(MultipartFile file) {
        String filename = file.getOriginalFilename();
        String key = System.currentTimeMillis() + "_" + filename;
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(file.getContentType())
                .build();
        try (
                InputStream fileStream = file.getInputStream()
        ) {
            s3Client.putObject(request, RequestBody.fromInputStream(fileStream, file.getSize()));
            log.info("File uploaded successfully. bucket={}, key={}", bucketName, key);
        } catch (S3Exception e) {
            log.error("S3 upload failed. bucket={}, key={}, statusCode={}",
                    bucketName, key, e.statusCode(), e);
            throw new CustomException(HttpStatus.valueOf(e.statusCode()), "File upload failed");
        } catch (IOException e) {
            log.error("IO error during file upload. bucket={}, key={}", bucketName, key, e);
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "File upload failed");
        }
        return key;
    }

}
