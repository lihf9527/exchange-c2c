package com.exchange.c2c.web.controller;

import com.exchange.c2c.common.Result;
import com.exchange.c2c.common.annotation.Login;
import com.exchange.c2c.common.annotation.LoginUser;
import com.exchange.c2c.entity.User;
import com.exchange.c2c.web.model.CreateAdvertForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@Api(tags = "广告接口")
@Validated
@RestController
@RequestMapping("/advert")
public class AdvertController {

    @Login
    @PostMapping("/create")
    @ApiOperation(value = "新增广告", notes = "创建人: 李海峰")
    public Result<CreateAdvertForm> create(@ApiIgnore @LoginUser User user, @Valid CreateAdvertForm form) {
        System.out.println(user);
        System.out.println(form);
        return Result.success(form);
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改广告", notes = "创建人: 李海峰")
    public Result<?> update() {

        return Result.SUCCESS;
    }

    @GetMapping("/info")
    @ApiOperation(value = "广告详情", notes = "创建人: 李海峰")
    public Result<?> info() {

        return Result.SUCCESS;
    }

    @PostMapping("/myAds")
    @ApiOperation(value = "我的广告列表", notes = "创建人: 李海峰")
    public Result<?> myAds() {

        return Result.SUCCESS;
    }

    @PostMapping("/list")
    @ApiOperation(value = "买卖市场广告列表", notes = "创建人: 李海峰")
    public Result<?> list() {

        return Result.SUCCESS;
    }
}
