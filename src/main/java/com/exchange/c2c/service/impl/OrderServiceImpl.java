package com.exchange.c2c.service.impl;

import com.exchange.c2c.common.exception.BizException;
import com.exchange.c2c.entity.Order;
import com.exchange.c2c.mapper.OrderMapper;
import com.exchange.c2c.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Order findById(Integer id) {
        return Optional.ofNullable(orderMapper.selectById(id)).orElseThrow(() -> new BizException("订单不存在"));
    }
}
