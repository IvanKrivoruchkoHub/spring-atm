package com.example.springatm.service.impl;

import com.example.springatm.entity.User;
import com.example.springatm.repository.UserRepository;
import com.example.springatm.service.UserService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findByLogin(String login) {
        Optional<User> user = userRepository.findByLogin(login);
        if (user.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Can't find user with login = " + login);
        }
        return user.get();
    }

    @Override
    public boolean isUserExist(String login) {
        return userRepository.findByLogin(login).isPresent();
    }
}
