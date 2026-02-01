package com.ecommerce.ecommerce.modules.inventory.service;

import com.ecommerce.ecommerce.common.exception.CustomException;
import com.ecommerce.ecommerce.modules.inventory.dto.InventoryInDTO;
import com.ecommerce.ecommerce.modules.inventory.dto.InventoryResDTO;
import com.ecommerce.ecommerce.modules.inventory.dto.LeftoverResDTO;
import com.ecommerce.ecommerce.modules.inventory.entity.InventoryTransaction;
import com.ecommerce.ecommerce.modules.inventory.entity.enums.Reason;
import com.ecommerce.ecommerce.modules.inventory.entity.enums.Type;
import com.ecommerce.ecommerce.modules.inventory.mapper.InventoryMapper;
import com.ecommerce.ecommerce.modules.inventory.repository.InventoryTransactionRepository;
import com.ecommerce.ecommerce.modules.order.entity.Order;
import com.ecommerce.ecommerce.modules.product.entity.Product;
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

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final ProductRepository productRepository;
    private final InventoryTransactionRepository inventoryTransactionRepository;
    private final InventoryMapper inventoryMapper;

    private ZoneId tz = ZoneId.of("Asia/Tashkent");

    @Override
    @Transactional
    public InventoryResDTO income(InventoryInDTO dto) {
        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> {
                    log.error("Product not found with id {}", dto.productId());
                    return new CustomException(HttpStatus.NOT_FOUND, "Product not found");
                });

        productRepository.addLeftover(dto.productId(), dto.quantity());

        InventoryTransaction inventory = InventoryTransaction.builder()
                .product(product)
                .type(Type.IN)
                .quantity(dto.quantity())
                .reason(Reason.RESTOCK)
                .build();
        InventoryTransaction saved = inventoryTransactionRepository.save(inventory);
        return inventoryMapper.toInventoryResDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public LeftoverResDTO leftover(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.error("Product not found with id {}", productId);
                    return new CustomException(HttpStatus.NOT_FOUND, "Product not found");
                });

        InventoryTransaction lastIncome = inventoryTransactionRepository
                .findFirstByProductAndTypeOrderByCreatedAtDesc(product, Type.IN)
                .orElse(null);
        LocalDateTime lastIncomeAt = (lastIncome == null)
                ? null
                : LocalDateTime.ofInstant(lastIncome.getCreatedAt(), tz);

        return new LeftoverResDTO(
                product.getId(),
                product.getLeftover(),
                lastIncomeAt);
    }

    @Override
    public Page<InventoryResDTO> history(UUID productId, Type type, LocalDate from, LocalDate to, Integer page, Integer size) {

        Specification<InventoryTransaction> spec = ((root, query, cb) -> cb.conjunction());

        if (productId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("product").get("id"), productId));
        }
        if (type != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("type"), type));
        }
        if (from != null) {
            Instant fromStart = from.atStartOfDay(tz).toInstant();
            spec = spec.and(((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("createdAt"), fromStart)));
        }
        if (to != null) {
            Instant toExclusive = to.plusDays(1).atStartOfDay(tz).toInstant();
            spec = spec.and(((root, query, cb) -> cb.lessThanOrEqualTo(root.get("createdAt"), toExclusive)));
        }

        int p = (page == null || page < 0) ? 0 : page;
        int s = (size == null || size <= 0) ? 10 : size;
        Pageable pageable = PageRequest.of(p, s, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<InventoryTransaction> all = inventoryTransactionRepository.findAll(spec, pageable);
        return all.map(inventoryMapper::toInventoryResDTO);
    }

    @Override
    public void outcomeWithOrder(Order order) {
        List<InventoryTransaction> all = new ArrayList<>();
        order.getItems().forEach(item -> {
            all.add(
                    InventoryTransaction.builder()
                            .product(item.getProduct())
                            .type(Type.OUT)
                            .quantity(item.getQuantity())
                            .reason(Reason.ORDER)
                            .reference_id(order.getId())
                            .build()
            );
        });
        inventoryTransactionRepository.saveAll(all);
    }

    @Override
    public void cancelOrder(Order order) {
        List<InventoryTransaction> all = new ArrayList<>();
        order.getItems().forEach(item -> {
            productRepository.addLeftover(item.getId(), item.getQuantity());
            all.add(
                    InventoryTransaction.builder()
                            .product(item.getProduct())
                            .type(Type.IN)
                            .quantity(item.getQuantity())
                            .reason(Reason.ORDER_CANCELED)
                            .reference_id(order.getId())
                            .build()
            );
        });
        inventoryTransactionRepository.saveAll(all);
    }
}
