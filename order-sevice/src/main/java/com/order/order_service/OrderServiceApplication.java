package com.order.order_service;

import com.order.order_service.domain.dtos.Order;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class OrderServiceApplication {
	List<Order> orders;
	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

	@Bean
	public List<Order> loadOrders(){

		 orders = List.of(
				new Order(1, 1, "Laptop", 1200.99),
				new Order(2, 2, "Smartphone", 800.00),
				new Order(3, 1, "Tablet", 300.00)
		);
		return orders;
	}

}
