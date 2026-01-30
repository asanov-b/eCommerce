package com.ecommerce.ecommerce.modules.product.controller;

import com.ecommerce.ecommerce.modules.product.dto.ProductResDTO;
import com.ecommerce.ecommerce.modules.product.dto.ProductDTO;
import com.ecommerce.ecommerce.modules.product.dto.ProductSearchDTO;
import com.ecommerce.ecommerce.modules.product.service.ProductsService;
import com.ecommerce.ecommerce.modules.product.validation.OnCreate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

import static com.ecommerce.ecommerce.common.utils.AppPaths.*;


@RestController
@RequestMapping(API +VERSION+"/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductsService productsService;

    @GetMapping
    public ResponseEntity<Page<ProductResDTO>> getProducts(
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ){
        Page<ProductResDTO> products = productsService.getAll(categoryId, page, size);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResDTO> getProduct(@PathVariable UUID id){
        ProductResDTO product = productsService.getProduct(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<ProductResDTO> addProduct(@Validated(OnCreate.class) @RequestBody ProductDTO productDTO){
        ProductResDTO saved = productsService.save(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable UUID id, @Valid @RequestBody ProductDTO productDTO){
        ProductResDTO updated = productsService.update(id, productDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable UUID id){
        productsService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    public ResponseEntity<Page<ProductResDTO>> search(@Valid @RequestBody ProductSearchDTO productSearchDTO){
        Page<ProductResDTO> page = productsService.search(productSearchDTO);
        return ResponseEntity.ok(page);
    }
}
