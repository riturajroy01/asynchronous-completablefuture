package com.asynchronous.completablefuture.service;

import com.asynchronous.completablefuture.domain.dtos.User;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
@Service
public class ReactiveUserClientService {
    private final WebClient webClient;

    public ReactiveUserClientService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:8086").build();
    }

    // Applies circuit breaker + retry (configured by name in application.yml)
    @CircuitBreaker(name = "userServiceCB", fallbackMethod = "fallbackUser")
    @Retry(name = "userServiceRetry")
    public Mono<User> getUserById(Integer userId) {
        return webClient.get()
                .uri("/api/users/{id}", userId)
                .retrieve()
                .bodyToMono(User.class)
                // reactive timeout: acts as a TimeLimiter (throws TimeoutException)
                .timeout(Duration.ofSeconds(2));
    }

    // Fallback must match parameters + Throwable at end
    private Mono<User> fallbackUser(Long userId, Throwable ex) {
        // log the error, return graceful fallback mono
        System.err.println("User service fallback: " + ex.toString());
        User fallback = new User();
        fallback.setId(userId);
        fallback.setName("Unknown");
        fallback.setEmail("unavailable@example.com");
        return Mono.just(fallback);
    }
}
