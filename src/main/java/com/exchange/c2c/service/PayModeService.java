package com.exchange.c2c.service;

import com.exchange.c2c.entity.PayMode;

public interface PayModeService {
    PayMode findById(Integer id);

    void save(PayMode payMode);

    void enable(Integer id);

    void disable(Integer id);
}
