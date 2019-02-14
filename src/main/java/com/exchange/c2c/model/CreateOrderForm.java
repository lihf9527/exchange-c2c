package com.exchange.c2c.model;

import com.exchange.c2c.common.annotation.EnumValue;
import com.exchange.c2c.enums.PayModeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@ApiModel
public class CreateOrderForm {
    @NotNull(message = "广告ID不能为空")
    @ApiModelProperty("广告ID")
    private Integer advId;

    @EnumValue(message = "支付方式枚举值不正确", enumClass = PayModeEnum.class)
    @NotEmpty(message = "支付方式不能为空")
    @ApiModelProperty("支付方式 1支付宝 2微信 3银行卡")
    private String payMode;

    @NotNull(message = "数量/总价 不能为空")
    @ApiModelProperty("数量/总价")
    private BigDecimal amount;
}
