package com.exchange.c2c.service;

import com.exchange.c2c.entity.User;
import com.exchange.c2c.web.model.LoginForm;

public interface UserService {
    User findById(Long userId);

    String login(LoginForm form);
}
