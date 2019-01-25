package com.exchange.c2c.web.controller;

import com.exchange.c2c.common.Result;
import com.exchange.c2c.service.UserService;
import com.exchange.c2c.web.model.LoginForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "用户接口")
@Validated
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ApiOperation(value = "用户登录", notes = "创建人: 李海峰")
    public Result<String> login(@Valid LoginForm form) {
        return Result.success(userService.login(form));
    }
}
