package com.exchange.c2c.model;

import com.exchange.c2c.common.annotation.EnumValue;
import com.exchange.c2c.enums.AccountTypeEnum;
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
    @Size(message = "用户名长度不能大于20", max = 20)
    @NotEmpty(message = "用户名不能为空")
    @ApiModelProperty("用户名")
    private String userName;

    @EnumValue(message = "账号类型枚举值不正确", enumClass = AccountTypeEnum.class)
    @NotNull(message = "账号类型不能为空")
    @ApiModelProperty("账号类型: 1 - 支付宝, 2 - 微信, 3 - 银行卡")
    private Integer accountType;

    @Size(message = "账号长度不能大于20", max = 20)
    @NotEmpty(message = "账号不能为空")
    @ApiModelProperty("微信/支付宝/银行卡 账号")
    private String account;

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

    @Pattern(message = "谷歌验证码格式错误", regexp = "\\d{6}")
    @NotEmpty(message = "谷歌验证码不能为空")
    @ApiModelProperty("谷歌验证码")
    private String googleCode;

    public interface BankCard {

    }

}
