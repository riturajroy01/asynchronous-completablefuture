package com.asynchronous.completablefuture.service.FullReactiveImpl;

import com.asynchronous.completablefuture.domain.dtos.Order;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
@Service
public class ReactiveOrderClientService {
    private final WebClient webClient;

    public ReactiveOrderClientService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:8087").build();
    }

    @CircuitBreaker(name = "orderServiceCB", fallbackMethod = "fallbackOrders")
    @Retry(name = "orderServiceRetry")
    public Mono<List<Order>> getOrdersByUserId(Integer userId) {
        return webClient.get()
                .uri("/api/orders/{userId}", userId)
                .retrieve()
                .bodyToFlux(Order.class)
                .collectList()
                .timeout(Duration.ofSeconds(2)); // reactive timeout
    }

    private Mono<List<Order>> fallbackOrders(Integer userId, Throwable ex) {
        System.err.println("Order service fallback: " + ex.toString());
        return Mono.just(List.of()); // empty list as fallback
    }
}
