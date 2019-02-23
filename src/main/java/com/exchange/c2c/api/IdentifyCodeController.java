package com.exchange.c2c.api;

import com.exchange.c2c.common.Result;
import com.exchange.c2c.common.util.Assert;
import com.exchange.c2c.common.util.RandomUtils;
import com.exchange.c2c.service.EmailService;
import com.exchange.c2c.service.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.concurrent.TimeUnit;

@Api(tags = "验证码接口")
@Validated
@RestController
@RequestMapping("/identifyCode")
public class IdentifyCodeController {
    private static final String template = "【ALiCoin】您的验证码：#code#，为了保护您的账户安全，请不要把验证码透露给别人";

    @Autowired
    private SmsService smsService;
    @Autowired
    private EmailService emailService;

    @Autowired
    private ValueOperations<String, String> valueOperations;

    @GetMapping("/phone")
    @ApiOperation(value = "获得短信验证码", notes = "创建人: 李海峰")
    public Result<?> phone(@RequestParam
                           @Pattern(message = "手机号码格式不正确", regexp = "")
                           @ApiParam("手机号码") String phone) {
        String code = RandomUtils.randomString(6);
        boolean result = smsService.send(phone, template.replace("#code#", code));
        Assert.isTrue(result, "短信发送失败,请稍后重试");
        valueOperations.set("identify_code_" + phone, code, 60, TimeUnit.SECONDS);
        return Result.SUCCESS;
    }

    @PostMapping("/email")
    @ApiOperation(value = "获得邮箱验证码", notes = "创建人: 李海峰")
    public Result<?> email(@RequestParam
                           @Email(message = "邮箱账号格式不正确", regexp = "")
                           @ApiParam("邮箱账号") String email) {
        String code = RandomUtils.randomString(6);
        boolean result = emailService.send(email, "ALiCoin", template.replace("#code#", code));
        Assert.isTrue(result, "邮件发送失败,请稍后重试");
        valueOperations.set("identify_code_" + email, code, 60, TimeUnit.SECONDS);
        return Result.SUCCESS;
    }
}
