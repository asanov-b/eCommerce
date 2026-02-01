package com.ecommerce.ecommerce.modules.inventory.service;

import com.ecommerce.ecommerce.modules.inventory.dto.InventoryInDTO;
import com.ecommerce.ecommerce.modules.inventory.dto.InventoryResDTO;
import com.ecommerce.ecommerce.modules.inventory.dto.LeftoverResDTO;
import com.ecommerce.ecommerce.modules.inventory.entity.enums.Type;
import com.ecommerce.ecommerce.modules.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDate;
import java.util.UUID;

public interface InventoryService {
    @PreAuthorize("hasRole('ADMIN')")
    InventoryResDTO income(InventoryInDTO inventoryInDTO);

    @PreAuthorize("hasRole('ADMIN')")
    LeftoverResDTO leftover(UUID productId);

    @PreAuthorize("hasRole('ADMIN')")
    Page<InventoryResDTO> history(UUID productId, Type type, LocalDate from, LocalDate to, Integer page, Integer size);

    void outcomeWithOrder(Order order);

    void cancelOrder(Order order);
}
