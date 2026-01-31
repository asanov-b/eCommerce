package com.ecommerce.ecommerce.modules.order.service;

import com.ecommerce.ecommerce.common.exception.CustomException;
import com.ecommerce.ecommerce.modules.order.dto.request.CreateOrderItemsDTO;
import com.ecommerce.ecommerce.modules.order.dto.request.UpdateOrderStatusDTO;
import com.ecommerce.ecommerce.modules.order.dto.response.OrderResDTO;
import com.ecommerce.ecommerce.modules.order.entity.Order;
import com.ecommerce.ecommerce.modules.order.entity.OrderItem;
import com.ecommerce.ecommerce.modules.order.entity.enums.OrderStatus;
import com.ecommerce.ecommerce.modules.order.mapper.OrderMapper;
import com.ecommerce.ecommerce.modules.order.repository.OrderRepository;
import com.ecommerce.ecommerce.modules.user.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
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
    private final OrderItemsService orderItemsService;
    private final EntityManager entityManager;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderResDTO create(List<CreateOrderItemsDTO> orderItemsDTO, UUID id) {

        User userRef = entityManager.getReference(User.class, id);

        for (int i = 0; i < 3; i++) {
            try {
                List<OrderItem> orderItems =
                        orderItemsService.updateLeftoverReturnOrderItems(orderItemsDTO); // optimistic locking exception here

                Order order = new Order();
                order.setUser(userRef);
                BigDecimal totalPrice = BigDecimal.ZERO;
                for (OrderItem item : orderItems) {
                    order.addItem(item);
                    totalPrice = totalPrice.add(item.getTotalAmount());
                }
                order.setTotalPrice(totalPrice);
                Order savedOrder = orderRepository.save(order);

                log.info("Order created successfully userId={} orderId={} totalPrice={}",
                        userRef.getId(),
                        savedOrder.getId(),
                        totalPrice);
                return orderMapper.toOrderResDTO(savedOrder);
            } catch (ObjectOptimisticLockingFailureException e) {
                log.warn("Optimistic locking failure on attempt {}/3 userId={}",
                        i + 1, userRef.getId());
            }
        }
        log.error("Order create failed after 3 retries userId={} itemsCount={}", userRef.getId(), orderItemsDTO.size());
        throw new CustomException(HttpStatus.TOO_MANY_REQUESTS, "Too many requests");
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
    public OrderResDTO getOrder(UUID orderId, UUID id) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            log.warn("Order not found. id {}", orderId);
            return new CustomException(HttpStatus.NOT_FOUND, "Order not found");
        });
        if (!order.getUser().getId().equals(id)) {
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
                    cb.equal(root.get("status"), status));
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
        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            log.warn("Order not found. id {}", orderId);
            return new CustomException(HttpStatus.NOT_FOUND, "Order not found");
        });
        if (order.getOrderStatus().equals(OrderStatus.CANCELED) || order.getOrderStatus().equals(OrderStatus.DELIVERED)) {
            log.warn("Order status not updated. orderId={}", orderId);
            throw new CustomException(HttpStatus.CONFLICT, "Order status not updated");
        }
        order.setOrderStatus(statusDTO.status());
        orderRepository.save(order);
        log.info("Order updated successfully. orderId={}", orderId);
        return orderMapper.toOrderResDTO(order);
    }
}
