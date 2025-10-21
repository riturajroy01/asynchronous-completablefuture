package com.asynchronous.completablefuture.service.completablefutureimpl;




import com.asynchronous.completablefuture.domain.dtos.User;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CompletableFuture;

@Service
public class UserClientService {

    private final WebClient webClient;

    public UserClientService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:8086").build();
    }

    @Async("asyncExecutor")
    @CircuitBreaker(name = "userServiceCB", fallbackMethod = "fallbackUser")
    @TimeLimiter(name = "userServiceTL", fallbackMethod = "fallbackUser")
    @Retry(name = "userServiceRetry" , fallbackMethod = "fallbackUser")
    public CompletableFuture<User> getUserById(Integer userId) {
        return CompletableFuture.supplyAsync(() ->
                webClient.get()
                        .uri("/api/users/{id}", userId)
                        .retrieve()
                        .bodyToMono(User.class)
                        .block()
        );
    }

    private CompletableFuture<User> fallbackUser(Integer userId, Throwable ex) {
        System.err.println("User service failed: " + ex.getMessage());
        User fallback = new User();
        fallback.setId(userId);
        fallback.setName("Unknown User");
        fallback.setEmail("unavailable@example.com");
        return CompletableFuture.completedFuture(fallback);
    }
}

