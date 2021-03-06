package com.exchange.c2c.model;

import com.exchange.c2c.common.annotation.ConfigValue;
import com.exchange.c2c.common.annotation.EnumValue;
import com.exchange.c2c.enums.VerifyModeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@ApiModel
public class CreatePayModeForm {
    @Size(message = "账户名长度不能大于100", max = 100)
    @NotEmpty(message = "账户名不能为空")
    @ApiModelProperty("账户名")
    private String accountName;

    @ConfigValue(message = "账号类型枚举值不正确", prefix = "account_type")
    @NotNull(message = "账号类型不能为空")
    @ApiModelProperty("账号类型: 1 - 支付宝, 2 - 微信, 3 - 银行卡")
    private String accountType;

    @Size(message = "账号长度不能大于20", max = 20)
    @NotEmpty(message = "账号不能为空")
    @ApiModelProperty("微信/支付宝/银行卡 账号")
    private String accountNumber;

    @Size(message = "收款二维码长度不能大于512", max = 512)
    @ApiModelProperty("微信/支付宝 收款二维码")
    private String qrCode;

    @Size(message = "开户银行长度不能大于10", max = 10)
    @NotEmpty(message = "开户银行不能为空", groups = BankCard.class)
    @ApiModelProperty("开户银行")
    private String bank;

    @Size(message = "开户支行长度不能大于25", max = 25)
    @ApiModelProperty("开户支行")
    private String branchBank;

    @EnumValue(message = "验证方式枚举值不正确", enumClass = VerifyModeEnum.class)
    @NotNull(message = "验证方式不能为空")
    @ApiModelProperty("验证方式 1手机验证 2邮箱验证 3谷歌验证")
    private Integer verifyMode;

    @Pattern(message = "验证码格式错误", regexp = "\\d{6}")
    @NotEmpty(message = "验证码不能为空")
    @ApiModelProperty("验证码")
    private String code;

    public interface BankCard {

    }

}
