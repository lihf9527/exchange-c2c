package com.exchange.c2c.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exchange.c2c.common.EnumMsg;
import com.exchange.c2c.common.exception.BizException;
import com.exchange.c2c.common.util.EnumUtils;
import com.exchange.c2c.common.util.WebUtils;
import com.exchange.c2c.entity.Order;
import com.exchange.c2c.entity.OrderDetail;
import com.exchange.c2c.enums.AdvertTypeEnum;
import com.exchange.c2c.enums.CurrentOrderStatusEnum;
import com.exchange.c2c.enums.OrderStatusEnum;
import com.exchange.c2c.enums.TradingTypeEnum;
import com.exchange.c2c.mapper.CurrencyMapper;
import com.exchange.c2c.mapper.OrderDetailMapper;
import com.exchange.c2c.mapper.OrderMapper;
import com.exchange.c2c.model.PaymentConfirmForm;
import com.exchange.c2c.model.QueryOrderForm;
import com.exchange.c2c.service.AccountService;
import com.exchange.c2c.service.AdvertService;
import com.exchange.c2c.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private CurrencyMapper currencyMapper;

    @Autowired
    private AccountService accountService;
    @Autowired
    private AdvertService advertService;

    @Override
    public Order findById(Integer id) {
        return Optional.ofNullable(orderMapper.selectById(id)).orElseThrow(() -> new BizException("订单不存在"));
    }

    @Override
    public Order findByOrderNo(String orderNo) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        return Optional.ofNullable(orderMapper.selectOne(wrapper)).orElseThrow(() -> new BizException("订单不存在"));
    }

    @Override
    public Order getUnfinishedOrder(Long userId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("create_by", userId);
        wrapper.in("status", EnumUtils.getValues(CurrentOrderStatusEnum.class));
        return orderMapper.selectOne(wrapper);
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

    @Override
    public long countTodayCancelledOrders(Long userId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("buyer_id", userId);
        wrapper.eq("create_by", userId);
        wrapper.eq("status", OrderStatusEnum.CANCELED.getValue());
        wrapper.ge("cancel_time", LocalDate.now());
        wrapper.lt("cancel_time", LocalDate.now().plusDays(1));
        return orderMapper.selectCount(wrapper);
    }

    @Override
    public long countSellerFinishedOrders(Long userId, Integer advertType) {
        return countSellerOrders(userId, advertType, OrderStatusEnum.FINISHED.getValue());
    }

    @Override
    public long countSellerAllOrders(Long userId, Integer advertType) {
        return countSellerOrders(userId, advertType, null);
    }

    @Override
    @Transactional
    public void confirm(PaymentConfirmForm form) {
        Order order = new Order();
        order.setId(form.getId());
        order.setTransferPayMode(form.getPayMode());
        order.setTransferVoucher(form.getVoucher());
        order.setPayTime(LocalDateTime.now());
        order.setStatus(OrderStatusEnum.WAIT_CONFIRM.getValue());
        orderMapper.updateById(order);
    }

    @Override
    @Transactional
    public void confirm(Integer id) {
        Order order = findById(id);
        // 修改订单状态
        Order temp = new Order();
        temp.setId(order.getId());
        temp.setStatus(OrderStatusEnum.FINISHED.getValue());
        temp.setFinishTime(LocalDateTime.now());
        orderMapper.updateById(temp);
        // 转账
        Integer currencyId = currencyMapper.findCurrencyIdByCode(order.getCurrencyCode());
        accountService.transfer(currencyId, order.getSellerId(), order.getBuyerId(), order.getQuantity());
    }

    @Override
    @Transactional
    public void cancel(Integer id) {
        Order order = findById(id);
        // 修改订单状态
        Order temp = new Order();
        temp.setId(order.getId());
        temp.setStatus(OrderStatusEnum.CANCELED.getValue());
        temp.setCancelTime(LocalDateTime.now());
        orderMapper.updateById(temp);
        // 解冻
        Integer currencyId = currencyMapper.findCurrencyIdByCode(order.getCurrencyCode());
        accountService.unfreeze(currencyId, order.getSellerId(), order.getQuantity());
    }

    @Override
    @Transactional
    public void create(Order order, OrderDetail orderDetail) {
        if (Objects.equals(order.getSellerId(), order.getCreateBy())) {// 如果卖方是用户,冻结用户的资金
            Integer currencyId = currencyMapper.findCurrencyIdByCode(order.getCurrencyCode());
            accountService.freeze(currencyId, order.getSellerId(), order.getQuantity());
        }

        orderMapper.insert(order);
        orderDetailMapper.insert(orderDetail);
        advertService.decr(orderDetail.getAdNo(), order.getQuantity());
    }

    private long countSellerOrders(Long userId, Integer advertType, Integer status) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.ne("create_by", userId);
        if (Objects.equals(AdvertTypeEnum.BUY.getValue(), advertType)) {
            wrapper.eq("buyer_id", userId);
        } else {
            wrapper.eq("seller_id", userId);
        }
        if (Objects.nonNull(status)) {
            wrapper.eq("status", status);
        }
        return orderMapper.selectCount(wrapper);
    }
}
