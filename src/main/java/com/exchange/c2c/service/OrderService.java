package com.exchange.c2c.service;

import com.exchange.c2c.entity.Order;

public interface OrderService {
    Order findById(Integer id);
}
