package com.exchange.c2c.model;

import com.exchange.c2c.common.annotation.ConfigValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@ApiModel
public class CreateOrderForm {
    @NotNull(message = "广告编号不能为空")
    @ApiModelProperty("广告编号")
    private String adNo;

    @ConfigValue(message = "支付方式枚举值不正确", prefix = "account_type")
    @NotEmpty(message = "支付方式不能为空")
    @ApiModelProperty("支付方式 1支付宝 2微信 3银行卡")
    private String payMode;

    @Digits(message = "数量只能输入4位小数", integer = 20, fraction = 4)
    @NotNull(message = "数量不能为空")
    @ApiModelProperty("数量")
    private BigDecimal quantity;

    @Digits(message = "总价只能输入2位小数", integer = 20, fraction = 2)
    @NotNull(message = "总价不能为空")
    @ApiModelProperty("总价")
    private BigDecimal totalPrice;
}
