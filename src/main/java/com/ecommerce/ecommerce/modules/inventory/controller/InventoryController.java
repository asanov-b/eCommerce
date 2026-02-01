package com.ecommerce.ecommerce.modules.inventory.controller;

import com.ecommerce.ecommerce.modules.inventory.dto.InventoryInDTO;
import com.ecommerce.ecommerce.modules.inventory.dto.InventoryResDTO;
import com.ecommerce.ecommerce.modules.inventory.dto.LeftoverResDTO;
import com.ecommerce.ecommerce.modules.inventory.entity.enums.Type;
import com.ecommerce.ecommerce.modules.inventory.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

import static com.ecommerce.ecommerce.common.utils.AppPaths.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API+ VERSION+"/admin/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/in")
    public ResponseEntity<InventoryResDTO> income(@Valid @RequestBody InventoryInDTO inventoryInDTO){
        InventoryResDTO incomed = inventoryService.income(inventoryInDTO);
        return ResponseEntity.ok(incomed);
    }

    @GetMapping("/stock/{productId}")
    public ResponseEntity<LeftoverResDTO> leftover(@PathVariable UUID productId){
        LeftoverResDTO leftover = inventoryService.leftover(productId);
        return ResponseEntity.ok(leftover);
    }

    @GetMapping("/history")
    public ResponseEntity<Page<InventoryResDTO>> history(
            @RequestParam(required = false) UUID productId,
            @RequestParam(required = false) Type type,
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
            ){
        Page<InventoryResDTO> history = inventoryService.history(productId, type, from, to, page, size);
        return ResponseEntity.ok(history);
    }

}
