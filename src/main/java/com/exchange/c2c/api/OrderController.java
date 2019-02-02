package com.exchange.c2c.api;

import com.exchange.c2c.common.Result;
import com.exchange.c2c.common.annotation.Login;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "订单接口")
@Validated
@RestController
@RequestMapping("/order")
public class OrderController {

    @Login
    @PostMapping("/create")
    @ApiOperation(value = "提交订单", notes = "创建人: 李海峰")
    public Result<?> create() {

        return Result.SUCCESS;
    }

    @Login
    @PostMapping("/info")
    @ApiOperation(value = "订单详情", notes = "创建人: 李海峰")
    public Result<?> info() {

        return Result.SUCCESS;
    }

    @Login
    @PostMapping("/cancel")
    @ApiOperation(value = "取消订单", notes = "创建人: 李海峰")
    public Result<?> cancel() {

        return Result.SUCCESS;
    }

    @Login
    @PostMapping("/payment/confirm")
    @ApiOperation(value = "确认支付", notes = "创建人: 李海峰")
    public Result<?> paymentConfirm() {

        return Result.SUCCESS;
    }

    @Login
    @PostMapping("/receive/confirm")
    @ApiOperation(value = "确认收款", notes = "创建人: 李海峰")
    public Result<?> receiveConfirm() {

        return Result.SUCCESS;
    }

    @Login
    @PostMapping("/unfinished")
    @ApiOperation(value = "", notes = "创建人: 李海峰")
    public Result<?> unfinished() {

        return Result.SUCCESS;
    }

    @Login
    @PostMapping("/list")
    @ApiOperation(value = "", notes = "创建人: 李海峰")
    public Result<?> list() {

        return Result.SUCCESS;
    }
}
