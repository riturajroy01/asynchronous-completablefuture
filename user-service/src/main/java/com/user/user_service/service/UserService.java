package com.user.user_service.service;

import com.user.user_service.domain.dtos.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class UserService {

    Map<Integer, User> users;
    public User getUserById(Integer id){
        return users.get(id);
    }
}
