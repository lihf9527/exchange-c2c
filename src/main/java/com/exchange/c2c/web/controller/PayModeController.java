package com.exchange.c2c.web.controller;

import com.exchange.c2c.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "支付方式接口")
@Validated
@RestController
@RequestMapping("/payMode")
public class PayModeController {

    @PostMapping("/create")
    @ApiOperation(value = "添加支付方式", notes = "创建人: 李海峰")
    public Result<?> create() {

        return Result.SUCCESS;
    }

    @PostMapping("/info")
    @ApiOperation(value = "支付方式详情", notes = "创建人: 李海峰")
    public Result<?> info() {

        return Result.SUCCESS;
    }

    @PostMapping("/enable")
    @ApiOperation(value = "启用支付方式", notes = "创建人: 李海峰")
    public Result<?> enable() {

        return Result.SUCCESS;
    }

    @PostMapping("/disable")
    @ApiOperation(value = "禁用支付方式", notes = "创建人: 李海峰")
    public Result<?> disable() {

        return Result.SUCCESS;
    }

    @PostMapping("/list")
    @ApiOperation(value = "支付方式列表", notes = "创建人: 李海峰")
    public Result<?> list() {

        return Result.SUCCESS;
    }

    @PostMapping("/isEnabled")
    @ApiOperation(value = "支付方式是否启用", notes = "创建人: 李海峰")
    public Result<?> isEnabled() {

        return Result.SUCCESS;
    }

    @PostMapping("/transferMode")
    @ApiOperation(value = "转账方式列表", notes = "创建人: 李海峰")
    public Result<?> transferMode() {

        return Result.SUCCESS;
    }
}
