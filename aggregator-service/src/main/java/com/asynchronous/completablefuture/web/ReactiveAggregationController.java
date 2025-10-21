package com.asynchronous.completablefuture.web;

import com.asynchronous.completablefuture.domain.dtos.UserOrderResponse;
import com.asynchronous.completablefuture.service.FullReactiveImpl.ReactiveAggregationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/api/reactive/aggregate")
public class ReactiveAggregationController {
    private final ReactiveAggregationService reactiveAggregationService;


    @GetMapping("/user/{userId}")
    public Mono<UserOrderResponse> getUserWithOrders(@PathVariable Integer userId) {
        return reactiveAggregationService.getUserWithOrders(userId);
    }
}
