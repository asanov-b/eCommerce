package com.ecommerce.ecommerce.modules.product.controller;

import com.ecommerce.ecommerce.modules.product.dto.CategoryDTO;
import com.ecommerce.ecommerce.modules.product.dto.CategoryResDTO;
import com.ecommerce.ecommerce.modules.product.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.ecommerce.ecommerce.common.utils.AppPaths.*;

@RestController
@RequestMapping(API + VERSION+"/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public HttpEntity<List<CategoryResDTO>> getAllCategory(){
        List<CategoryResDTO> category = categoryService.getAllCategory();
        return ResponseEntity.ok(category);
    }

    @GetMapping("/{id}")
    public HttpEntity<CategoryResDTO> getCategory(@PathVariable UUID id){
        CategoryResDTO category = categoryService.getCategory(id);
        return ResponseEntity.ok(category);
    }

    @PreAuthorize("hasAuthority('CATEGORY_CREATE')")
    @PostMapping
    public HttpEntity<CategoryResDTO> addCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryResDTO saved = categoryService.save(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PreAuthorize("hasAuthority('CATEGORY_UPDATE')")
    @PutMapping("/{id}")
    public HttpEntity<?> updateCategory(@PathVariable UUID id, @Valid @RequestBody CategoryDTO categoryDTO){
        categoryService.update(id,categoryDTO);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('CATEGORY_DELETE')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteCategory(@PathVariable UUID id){
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
