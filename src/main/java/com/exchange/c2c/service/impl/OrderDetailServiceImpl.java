package com.exchange.c2c.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exchange.c2c.common.exception.BizException;
import com.exchange.c2c.entity.OrderDetail;
import com.exchange.c2c.mapper.OrderDetailMapper;
import com.exchange.c2c.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public OrderDetail findByOrderNo(String orderNo) {
        QueryWrapper<OrderDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        return Optional.ofNullable(orderDetailMapper.selectOne(wrapper)).orElseThrow(() -> new BizException("订单不存在"));
    }
}
