package com.rishabh.orderservice.service;

import com.rishabh.orderservice.DTO.OrderLineItemsDTO;
import com.rishabh.orderservice.DTO.OrderRequest;
import com.rishabh.orderservice.model.Order;
import com.rishabh.orderservice.model.OrderLineItems;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDTOList()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        order.setOrderLineItemsList(orderLineItems);

        orderRepository.save(order);

    }

    private OrderLineItems mapToDTO(OrderLineItemsDTO orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItems.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;

    }
}
