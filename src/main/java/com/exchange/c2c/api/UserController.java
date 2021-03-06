package com.exchange.c2c.api;

import com.exchange.c2c.common.Result;
import com.exchange.c2c.common.annotation.Login;
import com.exchange.c2c.common.util.ApiBeanUtils;
import com.exchange.c2c.common.util.WebUtils;
import com.exchange.c2c.model.LoginForm;
import com.exchange.c2c.model.UserDTO;
import com.exchange.c2c.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
        String token = userService.login(form);
        return Result.success(token);
    }

    @Login
    @GetMapping("/info")
    @ApiOperation(value = "获得登录的用户信息", notes = "创建人: 李海峰")
    public Result<UserDTO> info() {
        return Result.success(ApiBeanUtils.copyProperties(WebUtils.getLoginUser(), UserDTO::new));
    }
}
