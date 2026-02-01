package com.ecommerce.ecommerce.modules.order.service;

import com.ecommerce.ecommerce.common.exception.CustomException;
import com.ecommerce.ecommerce.modules.inventory.service.InventoryService;
import com.ecommerce.ecommerce.modules.order.dto.request.CreateOrderItemsDTO;
import com.ecommerce.ecommerce.modules.order.dto.request.UpdateOrderStatusDTO;
import com.ecommerce.ecommerce.modules.order.dto.response.OrderResDTO;
import com.ecommerce.ecommerce.modules.order.entity.Order;
import com.ecommerce.ecommerce.modules.order.entity.OrderItem;
import com.ecommerce.ecommerce.modules.order.entity.enums.OrderStatus;
import com.ecommerce.ecommerce.modules.order.mapper.OrderMapper;
import com.ecommerce.ecommerce.modules.order.repository.OrderRepository;
import com.ecommerce.ecommerce.modules.product.entity.Product;
import com.ecommerce.ecommerce.modules.product.repository.ProductRepository;
import com.ecommerce.ecommerce.modules.user.entity.Role;
import com.ecommerce.ecommerce.modules.user.entity.RoleName;
import com.ecommerce.ecommerce.modules.user.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final EntityManager entityManager;
    private final OrderMapper orderMapper;
    private final ProductRepository productRepository;
    private final InventoryService inventoryService;

    @Override
    @Transactional
    public OrderResDTO create(List<CreateOrderItemsDTO> orderItemsDTO, UUID id) {

        User userRef = entityManager.getReference(User.class, id);

        Order order = new Order();
        order.setUser(userRef);

        BigDecimal orderTotalPrice = BigDecimal.ZERO;

        for (CreateOrderItemsDTO item : orderItemsDTO) {
            Product product = productRepository.findById(item.productId())
                    .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Product not found productId=" + item.productId()));

            int update = productRepository.subtractLeftover(product.getId(), item.quantity());
            if (update == 0) {
                log.warn("Not enough leftover productId={} requestedQty={}", product.getId(), item.quantity());
                throw new CustomException(HttpStatus.CONFLICT, "Not enough leftover");
            }

            BigDecimal itemTotalPrice = product.getPrice().multiply(BigDecimal.valueOf(item.quantity()));
            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .productNameSnapshot(product.getName())
                    .unitPriceSnapshot(product.getPrice())
                    .quantity(item.quantity())
                    .totalAmount(itemTotalPrice)
                    .build();
            orderTotalPrice = orderTotalPrice.add(orderItem.getTotalAmount());
            order.addItem(orderItem);
        }
        order.setTotalPrice(orderTotalPrice);
        Order savedOrder = orderRepository.save(order);

        log.info("Order created successfully userId={} orderId={} orderTotalPrice={}",
                id,
                savedOrder.getId(),
                orderTotalPrice);
        return orderMapper.toOrderResDTO(savedOrder);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<OrderResDTO> getMyOrders(Integer page, Integer size, OrderStatus orderStatus, UUID id) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orders;
        if (orderStatus == null) {
            orders = orderRepository.findAllByUserId(id, pageable);
        } else {
            orders = orderRepository.findAllByOrderStatusAndUserId(orderStatus, id, pageable);
        }
        return orders.map(orderMapper::toOrderResDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public OrderResDTO getOrder(UUID orderId, UUID id, List<Role> roles) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Order not found. orderId=" + orderId));

        if (roles.stream().noneMatch(role -> role.getRole().equals(RoleName.ADMIN)) && !order.getUser().getId().equals(id)) {
            log.warn("Order id not match user id {}", id);
            throw new CustomException(HttpStatus.FORBIDDEN, "User not allowed to order");
        }
        return orderMapper.toOrderResDTO(order);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<OrderResDTO> getOrders(Integer page, Integer size, OrderStatus status, UUID userId) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Order> spec = ((root, query, cb) -> cb.conjunction());

        if (status != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("orderStatus"), status));
        }
        if (userId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("user").get("id"), userId));
        }
        Page<Order> orders = orderRepository.findAll(spec, pageable);
        return orders.map(orderMapper::toOrderResDTO);
    }

    @Transactional
    @Override
    public OrderResDTO updateOrderStatus(UUID orderId, UpdateOrderStatusDTO statusDTO) {
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new CustomException(HttpStatus.NOT_FOUND, "Order not found. orderId= " + orderId));

        OrderStatus newStatus = statusDTO.status();
        OrderStatus oldStatus = order.getOrderStatus();

        if (oldStatus.equals(OrderStatus.CANCELED) || oldStatus.equals(OrderStatus.DELIVERED)) {
            throw new CustomException(HttpStatus.CONFLICT, "Order status not updated. orderId= " + orderId);
        }

        if (newStatus.equals(OrderStatus.CANCELED) && (oldStatus.equals(OrderStatus.PROCESSING) || oldStatus.equals(OrderStatus.SHIPPED))) {
            inventoryService.cancelOrder(order);
        }

        if (newStatus.equals(OrderStatus.PROCESSING)) {
            inventoryService.outcomeWithOrder(order);
        }

        order.setOrderStatus(newStatus);
        orderRepository.save(order);
        log.info("Order status updated successfully. status= {}, orderId= {}", newStatus, orderId);
        return orderMapper.toOrderResDTO(order);
    }
}
