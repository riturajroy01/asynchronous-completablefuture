package com.asynchronous.completablefuture.service.FullReactiveImpl;

import com.asynchronous.completablefuture.domain.dtos.Order;
import com.asynchronous.completablefuture.domain.dtos.User;
import com.asynchronous.completablefuture.domain.dtos.UserOrderResponse;
import com.asynchronous.completablefuture.service.ReactiveOrderClientService;
import com.asynchronous.completablefuture.service.ReactiveUserClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
@Service
@AllArgsConstructor
public class ReactiveAggregationService {

    private final ReactiveUserClientService reactiveuserClientService;
    private final ReactiveOrderClientService reactiveorderClientService;


    public Mono<UserOrderResponse> getUserWithOrders(Integer userId) {
        Mono<User> userMono = reactiveuserClientService.getUserById(userId);
        Mono<List<Order>> ordersMono = reactiveorderClientService.getOrdersByUserId(userId);

        // Run both in parallel and combine when both complete
        return Mono.zip(userMono, ordersMono, (user, orders) -> new UserOrderResponse(user, orders));
    }
}
