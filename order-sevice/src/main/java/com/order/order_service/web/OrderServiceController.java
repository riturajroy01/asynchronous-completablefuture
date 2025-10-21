package com.order.order_service.web;


import com.order.order_service.domain.dtos.Order;
import com.order.order_service.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderServiceController {

    private OrderService orderService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable(name = "userId") Integer userId) {

        return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
    }
}
