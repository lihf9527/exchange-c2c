package com.exchange.c2c.model;

import com.exchange.c2c.common.annotation.ConfigValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class PaymentConfirmForm {
    @NotNull(message = "订单ID不能为空")
    @ApiModelProperty("订单ID")
    private Integer id;

    @ConfigValue(message = "支付方式枚举值不正确", prefix = "account_type")
    @NotEmpty(message = "支付方式不能为空")
    @ApiModelProperty("支付方式 1支付宝 2微信 3银行卡")
    private String payMode;

    @NotEmpty(message = "支付凭证不能为空")
    @ApiModelProperty("支付凭证")
    private String voucher;
}
