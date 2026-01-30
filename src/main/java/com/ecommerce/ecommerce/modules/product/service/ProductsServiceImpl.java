package com.ecommerce.ecommerce.modules.product.service;

import com.ecommerce.ecommerce.common.exception.CustomException;
import com.ecommerce.ecommerce.modules.product.dto.ProductResDTO;
import com.ecommerce.ecommerce.modules.product.dto.ProductDTO;
import com.ecommerce.ecommerce.modules.product.dto.ProductSearchDTO;
import com.ecommerce.ecommerce.modules.product.entity.Attachment;
import com.ecommerce.ecommerce.modules.product.entity.Category;
import com.ecommerce.ecommerce.modules.product.entity.Product;
import com.ecommerce.ecommerce.modules.product.mapper.ProductMapper;
import com.ecommerce.ecommerce.modules.product.repository.AttachmentRepository;
import com.ecommerce.ecommerce.modules.product.repository.CategoryRepository;
import com.ecommerce.ecommerce.modules.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductsServiceImpl implements ProductsService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final AttachmentRepository attachmentRepository;
    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    @Override
    public Page<ProductResDTO> getAll(UUID categoryId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        if (categoryId == null) {
            return productRepository.findAll(pageable).map(productMapper::toProductResDTO);
        }
        return productRepository.findAllByCategoryId(categoryId, pageable).map(productMapper::toProductResDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductResDTO getProduct(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Product not found. id={}", id);
                    return new CustomException(HttpStatus.NOT_FOUND, "Product not found");
                });
        return productMapper.toProductResDTO(product);
    }

    @Transactional
    @Override
    public ProductResDTO save(ProductDTO productDTO) {
        Product product = new Product();
        applyDto(product, productDTO);
        Product saved = productRepository.save(product);
        log.info("Saved product successful id={}", saved.getId());
        return productMapper.toProductResDTO(saved);
    }

    @Transactional
    @Override
    public ProductResDTO update(UUID id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Product not found. id={}", id);
                    return new CustomException(HttpStatus.NOT_FOUND, "Product not found");
                });

        applyDto(product, productDTO);
        log.info("Updated product successful id={}", product.getId());
        return productMapper.toProductResDTO(product);
    }

    private void applyDto(Product product, ProductDTO dto) {
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());

        if (dto.attachmentId() != null) {
            List<UUID> ids = dto.attachmentId();
            List<Attachment> attachments = attachmentRepository.findAllById(ids);
            if (attachments.size() != ids.size()) {
                log.warn("Some attachments not found. attachmentId={}", dto.attachmentId());
                throw new CustomException(HttpStatus.NOT_FOUND, "Attachment not found");
            }
            attachments.forEach(a -> a.setProduct(product));
            product.setAttachments(attachments);
        }

        if (dto.categoryId() != null) {
            Category category = categoryRepository.findById(dto.categoryId())
                    .orElseThrow(() -> {
                        log.warn("Category not found. categoryId={}", dto.categoryId());
                        return new CustomException(HttpStatus.NOT_FOUND, "Category not found");
                    });
            product.setCategory(category);
        }
    }

    @Override
    public void delete(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(() -> {
            log.warn("Product not found. id {}", id);
            return new CustomException(HttpStatus.NOT_FOUND, "Product not found");
        });
        productRepository.delete(product);
        log.info("Deleted product successful id={}", product.getId());
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ProductResDTO> search(ProductSearchDTO searchDTO) {
        Specification<Product> spec = (root, query, cb) -> cb.conjunction();

        if (searchDTO.getName() != null && !searchDTO.getName().isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(root.get("name"), "%" + searchDTO.getName() + "%"));
        }
        if (searchDTO.getDescription() != null && !searchDTO.getDescription().isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(root.get("description"), "%" + searchDTO.getDescription() + "%"));
        }
        if (searchDTO.getCategory() != null && !searchDTO.getCategory().isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(root.get("category").get("name"), "%" + searchDTO.getCategory() + "%"));
        }
        if (searchDTO.getMinPrice() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("price"), searchDTO.getMinPrice()));
        }
        if (searchDTO.getMaxPrice() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("price"), searchDTO.getMaxPrice()));
        }
        if (searchDTO.getInStock() != null) {
            if (searchDTO.getInStock()) {
                spec = spec.and((root, query, cb) ->
                        cb.greaterThan(root.get("leftover"), 0));
            } else {
                spec = spec.and((root, query, cb) ->
                        cb.equal(root.get("leftover"), 0));
            }
        }

        Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize(), Sort.by(searchDTO.getDirection(), searchDTO.getSort()));

        Page<Product> all = productRepository.findAll(spec, pageable);
        return all.map(productMapper::toProductResDTO);
    }
}
