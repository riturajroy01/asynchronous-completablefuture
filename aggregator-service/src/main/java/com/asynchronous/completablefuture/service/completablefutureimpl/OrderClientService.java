package com.asynchronous.completablefuture.service.completablefutureimpl;

import com.asynchronous.completablefuture.domain.dtos.Order;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class OrderClientService {

    private final WebClient webClient;

    public OrderClientService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:8087").build();
    }

    @Async("asyncExecutor")
    @CircuitBreaker(name = "orderServiceCB", fallbackMethod = "fallbackOrders")
    @TimeLimiter(name = "orderServiceTL")
    @Retry(name = "orderServiceRetry")
    public CompletableFuture<List<Order>> getOrdersByUserId(Integer userId) {
        return CompletableFuture.supplyAsync(() ->
                webClient.get()
                        .uri("/api/orders/{userId}", userId)
                        .retrieve()
                        .bodyToFlux(Order.class)
                        .collectList()
                        .block()
        );
    }

    private CompletableFuture<List<Order>> fallbackOrders(Integer userId, Throwable ex) {
        System.err.println("Order service failed: " + ex.getMessage());
        return CompletableFuture.completedFuture(List.of());
    }
}

