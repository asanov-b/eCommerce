package com.ecommerce.ecommerce.modules.order.service;

import com.ecommerce.ecommerce.common.exception.CustomException;
import com.ecommerce.ecommerce.modules.order.dto.request.CreateOrderItemsDTO;
import com.ecommerce.ecommerce.modules.order.entity.OrderItem;
import com.ecommerce.ecommerce.modules.product.entity.Product;
import com.ecommerce.ecommerce.modules.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderItemsService {

    private final ProductRepository productRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<OrderItem> updateLeftoverReturnOrderItems(List<CreateOrderItemsDTO> orderItemsDTOs) {
        Map<UUID, Integer> qtyByProductId = orderItemsDTOs.stream().collect(Collectors.toMap(
                CreateOrderItemsDTO::productId,
                CreateOrderItemsDTO::quantity
        ));
        List<Product> products = productRepository.findAllById(qtyByProductId.keySet());

        Map<UUID, Product> uuidProductMap = products.stream()
                .collect(Collectors.toMap(
                        Product::getId,
                        product -> product
                ));

        List<OrderItem> orderItems = new ArrayList<>();

        qtyByProductId.forEach((productId, quantity) -> {
            Product product = uuidProductMap.get(productId);
            if (product == null) {
                log.warn("Product not found productId={}", productId);
                throw new CustomException(HttpStatus.NOT_FOUND, "Product not found productId=" + productId);
            }
            if (product.getLeftover() < quantity) {
                log.warn("Not enough leftover productId={} left={} requested={}", productId, product.getLeftover(), quantity);
                throw new CustomException(HttpStatus.CONFLICT, "Not enough leftover");
            }

            product.setLeftover(product.getLeftover() - quantity);

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setProductNameSnapshot(product.getName());
            orderItem.setUnitPriceSnapshot(product.getPrice());
            orderItem.setQuantity(quantity);
            BigDecimal unitPrice = product.getPrice().multiply(BigDecimal.valueOf(quantity));
            orderItem.setTotalAmount(unitPrice);
            orderItems.add(orderItem);
        });
        productRepository.saveAll(products);

        return orderItems;
    }
}
