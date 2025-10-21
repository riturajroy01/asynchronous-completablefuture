package com.asynchronous.completablefuture.web;


import com.asynchronous.completablefuture.domain.dtos.UserOrderResponse;
import com.asynchronous.completablefuture.service.AggregationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/aggregate")
public class AggregationController {

    private final AggregationService aggregationService;

    public AggregationController(AggregationService aggregationService) {
        this.aggregationService = aggregationService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<CompletableFuture<UserOrderResponse>> getUserWithOrders(@PathVariable(name = "userId") Integer userId) {
        System.out.println("Received request for user ID: " + userId);
        return ResponseEntity.ok().body(aggregationService.getUserWithOrders(userId));
    }
}
