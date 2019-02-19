package com.exchange.c2c.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.exchange.c2c.common.EnumMsg;
import com.exchange.c2c.entity.Order;
import com.exchange.c2c.entity.OrderDetail;
import com.exchange.c2c.model.PaymentConfirmForm;
import com.exchange.c2c.model.QueryOrderForm;

public interface OrderService {
    Order findById(Integer id);

    Order findByOrderNo(String orderNo);

    Order getUnfinishedOrder(Long userId);

    <T extends EnumMsg<Integer>> IPage<Order> findAll(QueryOrderForm form, Class<T> enumClass);

    long countTodayCancelledOrders(Long userId);

    long countSellerFinishedOrders(Long userId, Integer advertType);

    long countSellerAllOrders(Long userId, Integer advertType);

    /**
     * 付款确认
     */
    void confirm(PaymentConfirmForm form);

    /**
     * 收款确认
     */
    void confirm(Integer id);

    void cancel(Integer id);

    void create(Order order, OrderDetail orderDetail);
}
