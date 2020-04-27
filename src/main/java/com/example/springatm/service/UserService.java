package com.example.springatm.service;

import com.example.springatm.entity.User;

public interface UserService {
    User save(User user);

    User findByLogin(String login);

    boolean isUserExist(String login);

    void changePassword(String login, String oldPassword, String newPassword);
}
