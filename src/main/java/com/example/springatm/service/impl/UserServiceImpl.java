package com.example.springatm.service.impl;

import com.example.springatm.entity.User;
import com.example.springatm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserService userService;

    @Override
    public User save(User user) {
        return userService.save(user);
    }
}
