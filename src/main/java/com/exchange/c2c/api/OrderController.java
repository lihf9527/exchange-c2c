package com.exchange.c2c.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.exchange.c2c.common.Result;
import com.exchange.c2c.common.annotation.Login;
import com.exchange.c2c.common.page.PageList;
import com.exchange.c2c.common.util.ApiBeanUtils;
import com.exchange.c2c.common.util.Assert;
import com.exchange.c2c.common.util.WebUtils;
import com.exchange.c2c.entity.Order;
import com.exchange.c2c.enums.*;
import com.exchange.c2c.model.CreateOrderForm;
import com.exchange.c2c.model.OrderDTO;
import com.exchange.c2c.model.PaymentConfirmForm;
import com.exchange.c2c.model.QueryOrderForm;
import com.exchange.c2c.service.AdvertService;
import com.exchange.c2c.service.OrderService;
import com.exchange.c2c.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

@Api(tags = "订单接口")
@Validated
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private AdvertService advertService;
    @Autowired
    private UserService userService;

    @Login
    @PostMapping("/create")
    @ApiOperation(value = "提交订单", notes = "创建人: 李海峰")
    public Result<?> create(@Valid CreateOrderForm form) {
        val advert = advertService.findById(form.getAdvId());
        return Result.SUCCESS;
    }

    @Login
    @PostMapping("/info")
    @ApiOperation(value = "订单详情", notes = "创建人: 李海峰")
    public Result<?> info(@ApiParam("订单ID") @RequestParam Integer id) {
        val dto = Optional.of(orderService.findById(id)).map(order -> {

            return null;
        }).get();
        return Result.SUCCESS;
    }

    @Login
    @PostMapping("/cancel")
    @ApiOperation(value = "取消订单", notes = "创建人: 李海峰")
    public Result<?> cancel(@ApiParam("订单ID") @RequestParam Integer id) {
        val order = orderService.findById(id);
        Assert.isEquals(order.getBuyerId(), WebUtils.getUserId(), "非法操作");
        Assert.isEquals(OrderStatusEnum.WAIT_PAY.getValue(), order.getStatus(), "不能重复取消");
        return Result.SUCCESS;
    }

    @Login
    @PostMapping("/payment/confirm")
    @ApiOperation(value = "确认支付", notes = "创建人: 李海峰")
    public Result<?> paymentConfirm(@Valid PaymentConfirmForm form) {
        val order = orderService.findById(form.getId());
        Assert.isEquals(order.getBuyerId(), WebUtils.getUserId(), "非法操作");
        Assert.isEquals(order.getStatus(), OrderStatusEnum.WAIT_PAY.getValue(), "不能重复确认");
        if (!Objects.equals(PayModeEnum.BANK_CARD_PAYMENT.getValue(), order.getSellerPayMode())) {
            Assert.isEquals(order.getSellerPayMode(), form.getPayMode(), "支付方式不匹配");
        }
        orderService.confirm(form);
        return Result.SUCCESS;
    }

    @Login
    @PostMapping("/receipts/confirm")
    @ApiOperation(value = "确认收款", notes = "创建人: 李海峰")
    public Result<?> receiptsConfirm(Integer id) {

        return Result.SUCCESS;
    }

    @Login
    @PostMapping("/unfinished")
    @ApiOperation(value = "未完成的订单", notes = "创建人: 李海峰")
    public Result<?> unfinished() {

        return Result.SUCCESS;
    }

    @Login
    @PostMapping("/current")
    @ApiOperation(value = "当前订单", notes = "创建人: 李海峰")
    public Result<PageList<OrderDTO>> current(@Validated(CurrentOrderStatusEnum.class) QueryOrderForm form) {
        return Result.success(convertToPageList(orderService.findAll(form, CurrentOrderStatusEnum.class)));
    }

    @Login
    @PostMapping("/history")
    @ApiOperation(value = "历史订单", notes = "创建人: 李海峰")
    public Result<PageList<OrderDTO>> history(@Validated(HistoryOrderStatusEnum.class) QueryOrderForm form) {
        return Result.success(convertToPageList(orderService.findAll(form, HistoryOrderStatusEnum.class)));
    }

    private PageList<OrderDTO> convertToPageList(IPage<Order> page) {
        return ApiBeanUtils.convertToPageList(page, order -> {
            boolean isBuyer = Objects.equals(order.getBuyerId(), WebUtils.getUserId());// 当前登录用户是否是买方
            OrderDTO dto = ApiBeanUtils.copyProperties(order, OrderDTO::new);
            dto.setType(isBuyer ? TradingTypeEnum.BUY.getValue() : TradingTypeEnum.SELL.getValue());
            dto.setTargetName(userService.getFullName(isBuyer ? order.getSellerId() : order.getBuyerId()));
            return dto;
        });
    }
}
