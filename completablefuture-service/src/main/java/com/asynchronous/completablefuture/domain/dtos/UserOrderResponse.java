package com.asynchronous.completablefuture.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOrderResponse {
    private User user;
    private List<Order> orders;

}
