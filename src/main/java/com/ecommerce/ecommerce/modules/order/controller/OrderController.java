package com.ecommerce.ecommerce.modules.order.controller;

import com.ecommerce.ecommerce.modules.order.dto.request.CreateOrderItemsDTO;
import com.ecommerce.ecommerce.modules.order.dto.request.UpdateOrderStatusDTO;
import com.ecommerce.ecommerce.modules.order.dto.response.OrderResDTO;
import com.ecommerce.ecommerce.modules.order.entity.enums.OrderStatus;
import com.ecommerce.ecommerce.modules.order.service.OrderService;
import com.ecommerce.ecommerce.modules.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.ecommerce.ecommerce.common.utils.AppPaths.API;
import static com.ecommerce.ecommerce.common.utils.AppPaths.VERSION;


@RestController
@RequestMapping(API + VERSION + "/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResDTO> save(@Valid @RequestBody List<CreateOrderItemsDTO> orderItemsDTO, @AuthenticationPrincipal User principal) {
        OrderResDTO savedOrder = orderService.create(orderItemsDTO, principal.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    @GetMapping("/my")
    public ResponseEntity<Page<OrderResDTO>> getMyOrders(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "10", required = false) Integer size,
            @RequestParam(required = false) OrderStatus status,
            @AuthenticationPrincipal User principal) {
        Page<OrderResDTO> orderResDTOs = orderService.getMyOrders(page, size, status, principal.getId());
        return ResponseEntity.ok(orderResDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResDTO> getOrder(@PathVariable UUID id, @AuthenticationPrincipal User principal) {
        OrderResDTO orderResDTO = orderService.getOrder(id, principal.getId(), principal.getRoles());
        return ResponseEntity.ok(orderResDTO);
    }

    @GetMapping
    public ResponseEntity<Page<OrderResDTO>> getOrders(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "10", required = false) Integer size,
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) UUID userId) {
        Page<OrderResDTO> orders = orderService.getOrders(page, size, status, userId);
        return ResponseEntity.ok(orders);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResDTO> updateOrderStatus(@PathVariable UUID id, @RequestBody UpdateOrderStatusDTO updateOrderStatusDTO) {
        OrderResDTO orderResDTO = orderService.updateOrderStatus(id, updateOrderStatusDTO);
        return ResponseEntity.ok(orderResDTO);
    }
}
