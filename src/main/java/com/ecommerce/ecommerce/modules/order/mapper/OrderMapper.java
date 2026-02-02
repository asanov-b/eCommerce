package com.ecommerce.ecommerce.modules.order.mapper;

import com.ecommerce.ecommerce.modules.order.dto.response.OrderItemResDTO;
import com.ecommerce.ecommerce.modules.order.dto.response.OrderResDTO;
import com.ecommerce.ecommerce.modules.order.entity.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapper {

    private final ZoneId zoneId = ZoneId.of("Asia/Tashkent");

    public OrderResDTO toOrderResDTO(Order order) {
        List<OrderItemResDTO> orderItemResDTOS = new ArrayList<>();
        order.getItems().forEach(item -> {
            OrderItemResDTO itemResDTO = new OrderItemResDTO(
                    item.getProduct().getId(),
                    item.getProductNameSnapshot(),
                    item.getUnitPriceSnapshot(),
                    item.getQuantity(),
                    item.getTotalAmount()
            );
            orderItemResDTOS.add(itemResDTO);
        });

        return new OrderResDTO(
                order.getId(),
                order.getOrderStatus(),
                order.getTotalPrice(),
                LocalDateTime.ofInstant(order.getCreatedAt(), zoneId),
                orderItemResDTOS
        );
    }
}
