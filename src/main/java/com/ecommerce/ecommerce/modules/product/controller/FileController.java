package com.ecommerce.ecommerce.modules.product.controller;

import com.ecommerce.ecommerce.modules.product.service.FileService;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

import static com.ecommerce.ecommerce.common.utils.AppPaths.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API + VERSION + "/file")
public class FileController {

    private final FileService fileService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<UUID>> saveFile(@RequestPart("files") @NotEmpty List<MultipartFile> files) {
        List<UUID> saved = fileService.save(files);
        return ResponseEntity.ok(saved);
    }
}
