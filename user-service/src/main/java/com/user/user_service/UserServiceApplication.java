package com.user.user_service;

import com.user.user_service.domain.dtos.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Map;

@SpringBootApplication
public class UserServiceApplication {

public Map<Integer, User> users;
	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Bean
	public Map<Integer, User> loadUsers(){
		users = Map.of(
				1, new User(1, "John Doe", "john@gmail.com"),
				2, new User(2, "Jane Smith", "smith@gmail.com"),
				3, new User(3, "Alice Johnson", "Johnson.alice@gmail.com"));
		return users;
	}
}
