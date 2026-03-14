package com.sebastian.dev.order_management.controller.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.sebastian.dev.order_management.controller.dto.OrderLineDTO;
import com.sebastian.dev.order_management.controller.dto.OrderRequestDTO;
import com.sebastian.dev.order_management.controller.dto.OrderResponseDTO;
import com.sebastian.dev.order_management.model.Order;
import com.sebastian.dev.order_management.model.OrderLine;

@Component
public class OrderDTOMapper {

    @NonNull
    @SuppressWarnings("null")
    public Order toOrder(@NonNull OrderRequestDTO dto) {
        Order o = Order.newInstance(dto.orderDate(), dto.customerId());
        
        return o; // Order.newInstance ensures a non-null value.
    }

    @NonNull
    @SuppressWarnings("null")
    public OrderResponseDTO toOrderResponseDTO(@NonNull Order o) {
        List<OrderLineDTO> linesDTO = toOrderLineDTOList(o.getOrderProducts()); // Order holds a non-null list.
        OrderResponseDTO dto = new OrderResponseDTO(o.getId(), o.getOrderDate(), o.getCustomerId(), linesDTO);

        return dto;
    }

    // Helper
    private List<OrderLineDTO> toOrderLineDTOList(@NonNull Collection<OrderLine> lines) {
        return lines.stream().map(
                l -> new OrderLineDTO(l.getProductId(), l.getProductName(), l.getSku(), l.getQuantity(),
                        l.getUnitPrice()))
                .collect(Collectors.toUnmodifiableList());
    }
}
