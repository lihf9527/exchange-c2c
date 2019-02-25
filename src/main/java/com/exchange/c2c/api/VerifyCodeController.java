package com.exchange.c2c.api;

import com.exchange.c2c.common.Constant;
import com.exchange.c2c.common.Result;
import com.exchange.c2c.common.util.Assert;
import com.exchange.c2c.service.VerifyCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Api(tags = "验证码接口")
@Validated
@RestController
@RequestMapping("/verifyCode")
public class VerifyCodeController {
    @Autowired
    private VerifyCodeService verifyCodeService;

    @GetMapping("/phone")
    @ApiOperation(value = "获得短信验证码", notes = "创建人: 李海峰")
    public Result<?> phone(@RequestParam
                           @Pattern(message = "手机号码格式不正确", regexp = Constant.REGEXP_PHONE)
                           @ApiParam("手机号码") String phone) {
        verify(phone);
        verifyCodeService.sendPhone(phone);
        return Result.SUCCESS;
    }

    @PostMapping("/email")
    @ApiOperation(value = "获得邮箱验证码", notes = "创建人: 李海峰")
    public Result<?> email(@RequestParam
                           @Email(message = "邮箱账号格式不正确", regexp = Constant.REGEXP_EMAIL)
                           @ApiParam("邮箱账号") String email) {
        verify(email);
        verifyCodeService.sendEmail(email);
        return Result.SUCCESS;
    }

    private void verify(String phoneOrEmail) {
        Long times = verifyCodeService.getSendTimes(phoneOrEmail);
        Assert.isFalse(times != null && times > 5, "1个小时只能发送5次验证码");
    }
}
