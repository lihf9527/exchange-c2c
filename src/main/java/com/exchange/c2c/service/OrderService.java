package com.exchange.c2c.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.exchange.c2c.common.EnumMsg;
import com.exchange.c2c.entity.Order;
import com.exchange.c2c.model.QueryOrderForm;

public interface OrderService {
    Order findById(Integer id);

    <T extends EnumMsg<Integer>> IPage<Order> findAll(QueryOrderForm form, Class<T> enumClass);
}
