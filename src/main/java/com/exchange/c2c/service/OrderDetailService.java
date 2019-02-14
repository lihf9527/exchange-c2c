package com.exchange.c2c.service;

import com.exchange.c2c.entity.OrderDetail;

public interface OrderDetailService {
    OrderDetail findByOrderNo(String orderNo);
}
