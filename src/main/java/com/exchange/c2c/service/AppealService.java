package com.exchange.c2c.service;

import com.exchange.c2c.entity.Appeal;

public interface AppealService {
    void insert(Appeal appeal);

    Appeal findByOrderNo(String orderNo);
}
