package com.exchange.c2c.model;

import com.exchange.c2c.common.annotation.EnumValue;
import com.exchange.c2c.enums.AdvertTypeEnum;
import com.exchange.c2c.enums.PayModeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@ApiModel
public class CreateAdvertForm {
    @EnumValue(message = "广告类型枚举值不正确", enumClass = AdvertTypeEnum.class)
    @NotEmpty(message = "广告类型不能为空")
    @ApiModelProperty("广告类型 1 - 购买, 2 - 出售")
    private String type;

    @Digits(message = "价格只能输入两位小数", integer = Integer.MAX_VALUE, fraction = 2)
    @NotNull(message = "单价不能为空")
    @ApiModelProperty("单价")
    private BigDecimal price;

    @NotNull(message = "最小值不能为空")
    @ApiModelProperty("单笔金额/数量限制 最小值")
    private Integer minValue;

    @NotNull(message = "最大值不能为空")
    @ApiModelProperty("单笔金额/数量限制 最大值")
    private Integer maxValue;

    @EnumValue(message = "支付方式枚举值不正确", enumClass = PayModeEnum.class, multiple = true)
    @NotEmpty(message = "支付方式不能为空")
    @ApiModelProperty("支付方式, 多种用逗号分隔")
    private String payModes;
}
