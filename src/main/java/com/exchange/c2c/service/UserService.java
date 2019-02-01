package com.exchange.c2c.service;

import com.exchange.c2c.entity.User;
import com.exchange.c2c.model.LoginForm;

public interface UserService {
    User findById(Long userId);

    String getFullName(Long userId);

    String login(LoginForm form);
}
