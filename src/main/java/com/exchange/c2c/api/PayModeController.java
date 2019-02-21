package com.exchange.c2c.api;

import com.exchange.c2c.common.Result;
import com.exchange.c2c.common.annotation.Login;
import com.exchange.c2c.common.page.PageList;
import com.exchange.c2c.common.util.ApiBeanUtils;
import com.exchange.c2c.common.util.Assert;
import com.exchange.c2c.common.util.ValidationUtils;
import com.exchange.c2c.common.util.WebUtils;
import com.exchange.c2c.entity.PayMode;
import com.exchange.c2c.enums.AccountTypeEnum;
import com.exchange.c2c.enums.PayModeStatusEnum;
import com.exchange.c2c.model.*;
import com.exchange.c2c.service.ConfigService;
import com.exchange.c2c.service.GoogleAuthService;
import com.exchange.c2c.service.PayModeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Api(tags = "支付方式接口")
@Validated
@RestController
@RequestMapping("/payMode")
public class PayModeController {
    @Autowired
    private PayModeService payModeService;
    @Autowired
    private GoogleAuthService googleAuthService;
    @Autowired
    private ConfigService configService;

    @Login
    @PostMapping("/create")
    @ApiOperation(value = "添加支付方式", notes = "创建人: 李海峰")
    public Result<Integer> create(@Valid CreatePayModeForm form) {
        PayMode payMode = buildPayMode(form);
        payMode.setStatus(PayModeStatusEnum.DISABLE.getValue());
        payMode.setCreateBy(WebUtils.getUserId());
        payMode.setCreateTime(LocalDateTime.now());
        payModeService.save(payMode);
        return Result.success(payMode.getId());
    }

    private PayMode buildPayMode(CreatePayModeForm form) {
        if (Objects.equals(AccountTypeEnum.BANK_CARD.getValue(), form.getAccountType())) {
            ValidationUtils.validate(form, CreatePayModeForm.BankCard.class);
        }

//        User loginUser = WebUtils.getLoginUser();
//        Assert.isEquals(1, loginUser.getGaEnabled(), "未绑定谷歌");
//
//        String secret = EncryptionUtil.decrypt(loginUser.getGaSecurityKey());
//        boolean valid = googleAuthService.checkCode(secret, Long.parseLong(form.getGoogleCode()), System.currentTimeMillis());
//        Assert.isTrue(valid, "谷歌验证码错误");

        PayMode payMode = ApiBeanUtils.copyProperties(form, PayMode::new);
        payMode.setUpdateTime(LocalDateTime.now());
        return payMode;
    }

    @Login
    @PostMapping("/update")
    @ApiOperation(value = "修改支付方式", notes = "创建人: 李海峰")
    public Result<?> update(@Valid UpdatePayModeForm form) {
        PayMode old = payModeService.findById(form.getId());
        Assert.isEquals(old.getCreateBy(), WebUtils.getUserId(), "非法操作");
        payModeService.save(buildPayMode(form));
        return Result.SUCCESS;
    }

    @Login
    @PostMapping("/info")
    @ApiOperation(value = "支付方式详情", notes = "创建人: 李海峰")
    public Result<PayModeDTO> info(@RequestParam @ApiParam("支付方式ID") Integer id) {
        val payMode = payModeService.findById(id);
        Assert.isEquals(payMode.getCreateBy(), WebUtils.getUserId(), "非法操作");
        val model = ApiBeanUtils.copyProperties(payMode, PayModeDTO::new);
        return Result.success(model);
    }

    @Login
    @PostMapping("/enable")
    @ApiOperation(value = "启用支付方式", notes = "创建人: 李海峰")
    public Result<?> enable(@RequestParam @ApiParam("支付方式ID") Integer id) {
        PayMode payMode = payModeService.findById(id);
        Assert.isEquals(payMode.getCreateBy(), WebUtils.getUserId(), "非法操作");
        Assert.isEquals(PayModeStatusEnum.DISABLE.getValue(), payMode.getStatus(), "不能重复启用");
        payModeService.enable(id);
        return Result.SUCCESS;
    }

    @Login
    @PostMapping("/disable")
    @ApiOperation(value = "禁用支付方式", notes = "创建人: 李海峰")
    public Result<?> disable(@RequestParam @ApiParam("支付方式ID") Integer id) {
        PayMode payMode = payModeService.findById(id);
        Assert.isEquals(payMode.getCreateBy(), WebUtils.getUserId(), "非法操作");
        Assert.isEquals(PayModeStatusEnum.ENABLE.getValue(), payMode.getStatus(), "不能重复禁用");
        payModeService.disable(id);
        return Result.SUCCESS;
    }

    @Login
    @PostMapping("/list")
    @ApiOperation(value = "支付方式列表", notes = "创建人: 李海峰")
    public Result<PageList<PayModeDTO>> list(@Valid PayModeForm form) {
        return Result.success(ApiBeanUtils.convertToPageList(payModeService.findAll(form), e -> ApiBeanUtils.copyProperties(e, PayModeDTO::new)));
    }

    @Login
    @PostMapping("/isEnabled")
    @ApiOperation(value = "支付方式是否启用", notes = "创建人: 李海峰")
    public Result<List<PayModeDTO>> isEnabled(String payModes) {
        val payModeList = payModeService.findEnabled(WebUtils.getUserId(), payModes.split(","));
        val payModeDTOS = payModeList.stream().map(e -> ApiBeanUtils.copyProperties(e, PayModeDTO::new)).collect(Collectors.toList());
        return Result.success(payModeDTOS);
    }

    @Login
    @GetMapping("/enabled")
    @ApiOperation(value = "已启用的支付方式下拉框", notes = "创建人: 李海峰")
    public Result<List<AccountTypeDTO>> enabled() {
        val accountTypes = payModeService.findEnabled(WebUtils.getUserId()).stream().map(PayMode::getAccountType).collect(Collectors.toList());
        val list = getAccountTypeDTOS().stream().filter(e -> accountTypes.contains(e.getValue())).collect(Collectors.toList());
        return Result.success(list);
    }

    @GetMapping("/all")
    @ApiOperation(value = "支付方式下拉框", notes = "创建人: 李海峰")
    public Result<List<AccountTypeDTO>> all() {
        return Result.success(getAccountTypeDTOS());
    }

    private List<AccountTypeDTO> getAccountTypeDTOS() {
        return configService.findAll("account_type").stream().map(config -> {
            AccountTypeDTO dto = new AccountTypeDTO();
            dto.setValue(config.getValue());
            dto.setName(config.getText());
            return dto;
        }).collect(Collectors.toList());
    }
}
