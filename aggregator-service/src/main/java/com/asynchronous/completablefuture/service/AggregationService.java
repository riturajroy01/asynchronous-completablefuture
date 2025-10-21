package com.asynchronous.completablefuture.service;

import com.asynchronous.completablefuture.domain.dtos.Order;
import com.asynchronous.completablefuture.domain.dtos.User;
import com.asynchronous.completablefuture.domain.dtos.UserOrderResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class AggregationService {

    private final UserClientService userClientService;
    private final OrderClientService orderClientService;

    public AggregationService(UserClientService userClientService,
                              OrderClientService orderClientService) {
        this.userClientService = userClientService;
        this.orderClientService = orderClientService;
    }

    public CompletableFuture<UserOrderResponse> getUserWithOrders(Integer userId) {
        CompletableFuture<User> userFuture = userClientService.getUserById(userId);
        CompletableFuture<List<Order>> orderFuture = orderClientService.getOrdersByUserId(userId);

        return userFuture.thenCombine(orderFuture, UserOrderResponse::new);
    }
}

