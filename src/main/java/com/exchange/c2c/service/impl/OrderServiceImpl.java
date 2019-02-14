package com.exchange.c2c.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exchange.c2c.common.EnumMsg;
import com.exchange.c2c.common.exception.BizException;
import com.exchange.c2c.common.util.EnumUtils;
import com.exchange.c2c.common.util.WebUtils;
import com.exchange.c2c.entity.Order;
import com.exchange.c2c.enums.TradingTypeEnum;
import com.exchange.c2c.mapper.OrderMapper;
import com.exchange.c2c.model.QueryOrderForm;
import com.exchange.c2c.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Order findById(Integer id) {
        return Optional.ofNullable(orderMapper.selectById(id)).orElseThrow(() -> new BizException("订单不存在"));
    }

    @Override
    public <T extends EnumMsg<Integer>> IPage<Order> findAll(QueryOrderForm form, Class<T> enumClass) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(form.getOrderNo())) {
            wrapper.like("order_no", form.getOrderNo());
        }

        if (Objects.isNull(form.getType())) {
            wrapper.eq("buyer_id", WebUtils.getUserId()).or().eq("seller_id", WebUtils.getUserId());
        } else if (Objects.equals(TradingTypeEnum.BUY.getValue(), form.getType())) {
            wrapper.eq("buyer_id", WebUtils.getUserId());
        } else if (Objects.equals(TradingTypeEnum.SELL.getValue(), form.getType())) {
            wrapper.eq("seller_id", WebUtils.getUserId());
        }

        if (Objects.isNull(form.getStatus())) {
            wrapper.in("status", EnumUtils.getValues(enumClass));
        } else {
            wrapper.eq("status", form.getStatus());
        }

        return orderMapper.selectPage(new Page<>(form.getPageIndex(), form.getPageSize()), wrapper);
    }
}
