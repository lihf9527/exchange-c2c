package com.exchange.c2c.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.exchange.c2c.common.Result;
import com.exchange.c2c.common.annotation.Login;
import com.exchange.c2c.common.page.PageList;
import com.exchange.c2c.common.util.*;
import com.exchange.c2c.entity.Advert;
import com.exchange.c2c.entity.Order;
import com.exchange.c2c.entity.OrderDetail;
import com.exchange.c2c.enums.*;
import com.exchange.c2c.model.*;
import com.exchange.c2c.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Api(tags = "订单接口")
@Validated
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private AdvertService advertService;
    @Autowired
    private UserService userService;
    @Autowired
    private PayModeService payModeService;

    @Login
    @PostMapping("/create")
    @ApiOperation(value = "提交订单", notes = "创建人: 李海峰")
    public Result<Integer> create(@Valid CreateOrderForm form) {
        Advert advert = advertService.findByAdNo(form.getAdNo());
        Assert.isEquals(AdvertStatusEnum.ENABLE.getValue(), advert.getStatus(), "广告已下架");
        Assert.notEquals(advert.getCreateBy(), WebUtils.getUserId(), "不能向自己下单");
        Assert.isNull(orderService.getUnfinishedOrder(WebUtils.getUserId()), "存在未完成订单");

        List<String> payModes = Arrays.asList(advert.getPayModes().split(","));
        Assert.isTrue(payModes.contains(form.getPayMode()), "支付方式不正确");

        boolean quantityLtMin = form.getQuantity().compareTo(advert.getQuantityLimitMin()) < 0;
        boolean quantityGtMax = form.getQuantity().compareTo(advert.getQuantityLimitMax()) > 0;
        boolean totalPriceLtMin = form.getTotalPrice().compareTo(advert.getMoneyLimitMin()) < 0;
        boolean totalPriceGtMax = form.getTotalPrice().compareTo(advert.getMoneyLimitMax()) > 0;
        Assert.isTrue(!quantityLtMin && !quantityGtMax || !totalPriceLtMin && !totalPriceGtMax, "超出限额");

        BigDecimal totalPrice = advert.getPrice().multiply(form.getQuantity());
        BigDecimal quantity = form.getTotalPrice().divide(advert.getPrice(), 4, RoundingMode.DOWN);
        Assert.isTrue(Objects.equals(NumberUtils.format(form.getTotalPrice()), NumberUtils.format(totalPrice))
                || Objects.equals(NumberUtils.format(form.getQuantity(), 4), quantity), "价格计算错误");

        boolean isBuyAd = Objects.equals(AdvertTypeEnum.BUY.getValue(), advert.getType());

        Order order = new Order();
        order.setOrderNo(RandomUtils.serialNumber(6));
        order.setPrice(advert.getPrice());
        order.setQuantity(form.getQuantity());
        order.setTotalPrice(form.getTotalPrice());
        order.setCurrencyCode(advert.getCurrencyCode());
        order.setLegalCurrencyCode(advert.getLegalCurrencyCode());
        order.setPayMode(form.getPayMode());
        order.setStatus(OrderStatusEnum.WAIT_PAY.getValue());
        order.setBuyerId(isBuyAd ? advert.getCreateBy() : WebUtils.getUserId());
        order.setSellerId(isBuyAd ? WebUtils.getUserId() : advert.getCreateBy());
        order.setCreateBy(WebUtils.getUserId());
        order.setCreateTime(LocalDateTime.now());

        val enabled = payModeService.findEnabled(order.getSellerId(), form.getPayMode());
        Assert.isFalse(enabled.isEmpty(), "支付方式未启用");
        val payMode = enabled.get(0);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderNo(order.getOrderNo());
        orderDetail.setAdNo(advert.getAdNo());
        orderDetail.setAdType(advert.getType());
        orderDetail.setAdTotalQuantity(advert.getTotalQuantity());
        orderDetail.setAdSurplusQuantity(advert.getSurplusQuantity());
        orderDetail.setAdQuantityLimitMin(advert.getQuantityLimitMin());
        orderDetail.setAdQuantityLimitMax(advert.getQuantityLimitMax());
        orderDetail.setAdMoneyLimitMin(advert.getMoneyLimitMin());
        orderDetail.setAdMoneyLimitMax(advert.getMoneyLimitMax());
        orderDetail.setAdPayModes(advert.getPayModes());
        orderDetail.setAdPayModeIds(advert.getPayModeIds());
        orderDetail.setAdRemark(advert.getRemark());
        orderDetail.setSellerAccountType(payMode.getAccountType());
        orderDetail.setSellerAccountName(payMode.getAccountName());
        orderDetail.setSellerAccountNumber(payMode.getAccountNumber());
        orderDetail.setSellerQrCode(payMode.getQrCode());
        orderDetail.setSellerBank(payMode.getBank());
        orderDetail.setSellerBranchBank(payMode.getBranchBank());

        orderService.create(order, orderDetail);
        return Result.success(order.getId());
    }

    @Login
    @GetMapping("/info")
    @ApiOperation(value = "订单详情", notes = "创建人: 李海峰")
    public Result<OrderInfoDTO> info(@ApiParam("订单ID") @RequestParam Integer id) {
        val order = orderService.findById(id);
        val validUserIds = Arrays.asList(order.getBuyerId(), order.getSellerId());
        Assert.isTrue(validUserIds.contains(WebUtils.getUserId()), "非法操作");
        return Result.success(convertToOrderInfoDTO(order));
    }

    private OrderInfoDTO convertToOrderInfoDTO(Order order) {
        if (Objects.isNull(order))
            return null;

        val orderDetail = orderDetailService.findByOrderNo(order.getOrderNo());
        OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
        BeanUtils.copyProperties(orderDetail, orderInfoDTO);
        BeanUtils.copyProperties(order, orderInfoDTO);
        orderInfoDTO.setType(Objects.equals(order.getBuyerId(), order.getCreateBy()) ? TradingTypeEnum.BUY.getValue() : TradingTypeEnum.SELL.getValue());
        orderInfoDTO.setIsSeller(Objects.equals(order.getSellerId(), WebUtils.getUserId()));
        orderInfoDTO.setBuyerName(userService.getFullName(order.getBuyerId()));
        orderInfoDTO.setSellerName(userService.getFullName(order.getSellerId()));
        return orderInfoDTO;
    }

    @Login
    @GetMapping("/cancel")
    @ApiOperation(value = "取消订单", notes = "创建人: 李海峰")
    public Result<?> cancel(@ApiParam("订单ID") @RequestParam Integer id) {
        val order = orderService.findById(id);
        Assert.isEquals(order.getBuyerId(), WebUtils.getUserId(), "非法操作");
        Assert.isEquals(OrderStatusEnum.WAIT_PAY.getValue(), order.getStatus(), "不能重复取消");
        orderService.cancel(id);
        return Result.SUCCESS;
    }

    @Login
    @PostMapping("/payment/confirm")
    @ApiOperation(value = "确认支付", notes = "创建人: 李海峰")
    public Result<?> paymentConfirm(@Valid PaymentConfirmForm form) {
        val order = orderService.findById(form.getId());
        Assert.isEquals(order.getBuyerId(), WebUtils.getUserId(), "非法操作");
        Assert.isEquals(order.getStatus(), OrderStatusEnum.WAIT_PAY.getValue(), "不能重复确认");
        orderService.confirm(form);
        return Result.SUCCESS;
    }

    @Login
    @GetMapping("/receipts/confirm")
    @ApiOperation(value = "确认收款", notes = "创建人: 李海峰")
    public Result<?> receiptsConfirm(@ApiParam("订单ID") @RequestParam Integer id) {
        Order order = orderService.findById(id);
        Assert.isEquals(order.getSellerId(), WebUtils.getUserId(), "非法操作");
        Assert.isEquals(order.getStatus(), OrderStatusEnum.WAIT_CONFIRM.getValue(), "不能重复确认");
        orderService.confirm(id);
        return Result.SUCCESS;
    }

    @Login
    @GetMapping("/unfinished")
    @ApiOperation(value = "未完成的订单", notes = "创建人: 李海峰")
    public Result<OrderInfoDTO> unfinished() {
        return Result.success(convertToOrderInfoDTO(orderService.getUnfinishedOrder(WebUtils.getUserId())));
    }

    @Login
    @PostMapping("/current")
    @ApiOperation(value = "当前订单", notes = "创建人: 李海峰")
    public Result<PageList<OrderDTO>> current(@Valid QueryOrderForm form) {
        ValidationUtils.validate(form, QueryOrderForm.Current.class);
        return Result.success(convertToPageList(orderService.findAll(form, CurrentOrderStatusEnum.class)));
    }

    @Login
    @PostMapping("/history")
    @ApiOperation(value = "历史订单", notes = "创建人: 李海峰")
    public Result<PageList<OrderDTO>> history(@Valid QueryOrderForm form) {
        ValidationUtils.validate(form, QueryOrderForm.History.class);
        return Result.success(convertToPageList(orderService.findAll(form, HistoryOrderStatusEnum.class)));
    }

    private PageList<OrderDTO> convertToPageList(IPage<Order> page) {
        return ApiBeanUtils.convertToPageList(page, order -> {
            boolean isBuyer = Objects.equals(order.getBuyerId(), WebUtils.getUserId());// 当前登录用户是否是买方
            OrderDTO orderDTO = ApiBeanUtils.copyProperties(order, OrderDTO::new);
            orderDTO.setType(isBuyer ? TradingTypeEnum.BUY.getValue() : TradingTypeEnum.SELL.getValue());
            orderDTO.setTargetName(userService.getFullName(isBuyer ? order.getSellerId() : order.getBuyerId()));
            return orderDTO;
        });
    }
}
