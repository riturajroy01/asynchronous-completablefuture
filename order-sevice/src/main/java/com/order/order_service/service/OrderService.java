package com.order.order_service.service;


import com.order.order_service.domain.dtos.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    List<Order> orders;
    public List<Order> getOrdersByUserId(Integer userId){

        return orders.stream()
                .filter(order -> order.getUserId().intValue() == userId.intValue())
                .toList();

    }

}
