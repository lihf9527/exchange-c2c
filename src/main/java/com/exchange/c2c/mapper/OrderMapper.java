package com.exchange.c2c.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exchange.c2c.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
