package com.ecommerce.ecommerce.modules.inventory.mapper;

import com.ecommerce.ecommerce.modules.inventory.dto.InventoryResDTO;
import com.ecommerce.ecommerce.modules.inventory.entity.InventoryTransaction;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class InventoryMapper {

    private final ZoneId zoneId = ZoneId.of("Asia/Tashkent");

    public InventoryResDTO toInventoryResDTO(InventoryTransaction inventory) {
        return new InventoryResDTO(
                inventory.getId(),
                inventory.getProduct().getId(),
                inventory.getType(),
                inventory.getReason(),
                inventory.getQuantity(),
                inventory.getProduct().getLeftover(),
                inventory.getReference_id(),
                LocalDateTime.ofInstant(inventory.getCreatedAt(), zoneId)
        );
    }
}
