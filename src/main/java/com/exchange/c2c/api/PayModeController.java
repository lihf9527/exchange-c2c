package com.exchange.c2c.api;

import com.exchange.c2c.common.Result;
import com.exchange.c2c.common.annotation.Login;
import com.exchange.c2c.common.page.PageList;
import com.exchange.c2c.common.util.*;
import com.exchange.c2c.entity.PayMode;
import com.exchange.c2c.entity.User;
import com.exchange.c2c.enums.AccountTypeEnum;
import com.exchange.c2c.enums.PayModeStatusEnum;
import com.exchange.c2c.model.CreatePayModeForm;
import com.exchange.c2c.model.PayModeForm;
import com.exchange.c2c.model.PayModeModel;
import com.exchange.c2c.model.UpdatePayModeForm;
import com.exchange.c2c.service.GoogleAuthService;
import com.exchange.c2c.service.PayModeService;
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
import java.time.LocalDateTime;
import java.util.Objects;

@Api(tags = "支付方式接口")
@Validated
@RestController
@RequestMapping("/payMode")
public class PayModeController {
    @Autowired
    private PayModeService payModeService;
    @Autowired
    private GoogleAuthService googleAuthService;

    @Login
    @PostMapping("/create")
    @ApiOperation(value = "添加支付方式", notes = "创建人: 李海峰")
    public Result<Integer> create(@Valid CreatePayModeForm form) {
        PayMode payMode = buildPayMode(form);
        payMode.setCreateTime(LocalDateTime.now());
        payModeService.save(payMode);
        return Result.success(payMode.getId());
    }

    private PayMode buildPayMode(CreatePayModeForm form) {
        if (Objects.equals(AccountTypeEnum.BANK_CARD.getValue(), form.getAccountType())) {
            ValidationUtils.validate(form, CreatePayModeForm.BankCard.class);
        }

        User loginUser = WebUtils.getLoginUser();
        Assert.isEquals(1, loginUser.getGaEnabled(), "未绑定谷歌");

        String secret = EncryptionUtil.decrypt(loginUser.getGaSecurityKey());
        boolean valid = googleAuthService.checkCode(secret, Long.parseLong(form.getGoogleCode()), System.currentTimeMillis());
        Assert.isTrue(valid, "谷歌验证码错误");

        PayMode payMode = ApiBeanUtils.copyProperties(form, PayMode::new);
        payMode.setUserId(loginUser.getUserId());
        payMode.setUpdateTime(LocalDateTime.now());
        payMode.setStatus(PayModeStatusEnum.DISABLE.getValue());
        return payMode;
    }

    @Login
    @PostMapping("/update")
    @ApiOperation(value = "修改支付方式", notes = "创建人: 李海峰")
    public Result<?> update(@Valid UpdatePayModeForm form) {
        PayMode old = payModeService.findById(form.getId());
        Assert.isEquals(old.getUserId(), WebUtils.getUserId(), "非法操作");
        payModeService.save(buildPayMode(form));
        return Result.SUCCESS;
    }

    @Login
    @PostMapping("/info")
    @ApiOperation(value = "支付方式详情", notes = "创建人: 李海峰")
    public Result<PayModeModel> info(@RequestParam @ApiParam("支付方式ID") Integer id) {
        val payMode = payModeService.findById(id);
        Assert.isEquals(payMode.getUserId(), WebUtils.getUserId(), "非法操作");
        val model = ApiBeanUtils.copyProperties(payMode, PayModeModel::new);
        return Result.success(model);
    }

    @Login
    @PostMapping("/enable")
    @ApiOperation(value = "启用支付方式", notes = "创建人: 李海峰")
    public Result<?> enable(@RequestParam @ApiParam("支付方式ID") Integer id) {
        PayMode payMode = payModeService.findById(id);
        Assert.isEquals(payMode.getUserId(), WebUtils.getUserId(), "非法操作");
        Assert.isEquals(PayModeStatusEnum.DISABLE.getValue(), payMode.getStatus(), "不能重复启用");
        payModeService.disableByAccountType(payMode.getAccountType());
        payModeService.enable(id);
        return Result.SUCCESS;
    }

    @Login
    @PostMapping("/disable")
    @ApiOperation(value = "禁用支付方式", notes = "创建人: 李海峰")
    public Result<?> disable(@RequestParam @ApiParam("支付方式ID") Integer id) {
        PayMode payMode = payModeService.findById(id);
        Assert.isEquals(payMode.getUserId(), WebUtils.getUserId(), "非法操作");
        Assert.isEquals(PayModeStatusEnum.ENABLE.getValue(), payMode.getStatus(), "不能重复禁用");
        payModeService.disable(id);
        return Result.SUCCESS;
    }

    @PostMapping("/list")
    @ApiOperation(value = "支付方式列表", notes = "创建人: 李海峰")
    public Result<PageList<PayModeModel>> list(@Valid PayModeForm form) {
        val page = payModeService.findAll(form);
        val pageList = ApiBeanUtils.convertToPageList(page, e -> ApiBeanUtils.copyProperties(e, PayModeModel::new));
        return Result.success(pageList);
    }

    @Login
    @PostMapping("/isEnabled")
    @ApiOperation(value = "支付方式是否启用", notes = "创建人: 李海峰")
    public Result<?> isEnabled() {

        return Result.SUCCESS;
    }

    @Login
    @PostMapping("/transferMode")
    @ApiOperation(value = "转账方式列表", notes = "创建人: 李海峰")
    public Result<?> transferMode() {

        return Result.SUCCESS;
    }
}