package com.exchange.c2c.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exchange.c2c.common.exception.BizException;
import com.exchange.c2c.entity.Appeal;
import com.exchange.c2c.mapper.AppealMapper;
import com.exchange.c2c.service.AppealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppealServiceImpl implements AppealService {
    @Autowired
    private AppealMapper appealMapper;

    @Override
    public void insert(Appeal appeal) {
        appealMapper.insert(appeal);
    }

    @Override
    public Appeal findByOrderId(Integer orderId) {
        QueryWrapper<Appeal> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id", orderId);
        return Optional.ofNullable(appealMapper.selectOne(wrapper)).orElseThrow(() -> new BizException("该订单没有申诉信息"));
    }
}
