package com.sebastian.dev.order_management.repository.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.sebastian.dev.order_management.model.Order;
import com.sebastian.dev.order_management.model.OrderLine;
import com.sebastian.dev.order_management.repository.entity.CustomerEntity;
import com.sebastian.dev.order_management.repository.entity.OrderEntity;
import com.sebastian.dev.order_management.repository.entity.OrderLineEntity;
import com.sebastian.dev.order_management.repository.entity.OrderLineId;

@Component
public class OrderMapper {

    /**
     * This mapper must be called always within a transaction, otherwise will throw a LazyInitializationException if not callet within a transaction. This because of the entity.getCustomer().getId()
     * @param entity the OrderEntity from which we'll map
     * @return an Order object. 
     */
    @NonNull
    public Order toOrder(@NonNull OrderEntity entity){
        List<OrderLine> lines= entity.getOrderProducts().stream().map(
            o -> new OrderLine(o.getId().getProductId(), o.getProductName(), o.getSku(), o.getQuantity(), o.getUnitPrice())
        ).collect(Collectors.toList());

        return Objects.requireNonNull(Order.reconstitute(entity.getOrderDate(), entity.getCustomer().getId(), lines, entity.getId()));
    }

    /**
     * The OrderLine object contains the productId for pragmatism. 
     * @param order Order object to map from. 
     * @param customer The CustomerEntity object to pass to the OrderEntity
     * @return an OrderEntity with the arguments passed. 
     */
    @NonNull
    public OrderEntity toOrderEntity(Order order, CustomerEntity customer){
        if(order == null){
            throw new IllegalArgumentException("Required valid Order object for mapping");
        }

        OrderEntity entity = new OrderEntity();
        List<OrderLineEntity> lines = order.getOrderProducts().stream().map(
            o -> toOrderLineEntity(o, entity)
        ).collect(Collectors.toList());

        entity.setId(order.getId());
        entity.setOrderDate(order.getOrderDate());
        entity.setCustomer(customer);
        entity.setOrderProducts(lines);

        return entity;
    }

    private OrderLineEntity toOrderLineEntity(OrderLine line, OrderEntity orderObject){
        if(line == null){
            throw new IllegalArgumentException("The OrderLine object is required for mapping");
        }
        OrderLineEntity entity = new OrderLineEntity();
        entity.setOrderObject(orderObject);
        
        OrderLineId id = new OrderLineId();
        id.setProductId(line.getProductId());
        entity.setId(id);
        
        entity.setProductName(line.getProductName());
        entity.setQuantity(line.getQuantity());
        entity.setSku(line.getSku());
        entity.setUnitPrice(line.getUnitPrice());
        
        return entity;
    }

}
