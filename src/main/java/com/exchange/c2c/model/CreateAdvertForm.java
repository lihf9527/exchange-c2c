package com.exchange.c2c.model;

import com.exchange.c2c.common.annotation.ConfigValue;
import com.exchange.c2c.common.annotation.EnumValue;
import com.exchange.c2c.enums.AdvertTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@ApiModel
public class CreateAdvertForm {
    @EnumValue(message = "广告类型枚举值不正确", enumClass = AdvertTypeEnum.class)
    @NotNull(message = "广告类型不能为空")
    @ApiModelProperty("广告类型 1 - 购买, 2 - 出售")
    private Integer type;

    @NotEmpty(message = "币种不能为空")
    @ApiModelProperty("币种")
    private String currencyCode;

    @NotEmpty(message = "法币类型不能为空")
    @ApiModelProperty("法币类型")
    private String legalCurrencyCode;

    @Digits(message = "价格只能输入2位小数", integer = 20, fraction = 2)
    @NotNull(message = "单价不能为空")
    @ApiModelProperty("单价")
    private BigDecimal price;

    @Digits(message = "数量只能输入4位小数", integer = 20, fraction = 4)
    @NotNull(message = "发布数量不能为空")
    @ApiModelProperty("发布数量")
    private BigDecimal totalQuantity;

    @Digits(message = "数量只能输入4位小数", integer = 20, fraction = 4)
    @NotNull(message = "数量单笔限额 最小值不能为空")
    @ApiModelProperty("数量单笔限额 最小值")
    private BigDecimal quantityLimitMin;

    @Digits(message = "数量只能输入4位小数", integer = 20, fraction = 4)
    @NotNull(message = "数量单笔限额 最大值不能为空")
    @ApiModelProperty("数量单笔限额 最大值")
    private BigDecimal quantityLimitMax;

    @Digits(message = "价格只能输入2位小数", integer = 20, fraction = 2)
    @NotNull(message = "金额单笔限额 最小值不能为空")
    @ApiModelProperty("金额单笔限额 最小值")
    private BigDecimal moneyLimitMin;

    @Digits(message = "价格只能输入2位小数", integer = 20, fraction = 2)
    @NotNull(message = "金额单笔限额 最大值不能为空")
    @ApiModelProperty("金额单笔限额 最大值")
    private BigDecimal moneyLimitMax;

    @Size(message = "广告备注仅限50字", max = 50)
    @ApiModelProperty("广告备注")
    private String remark;

    @ConfigValue(message = "支付方式枚举值不正确", prefix = "account_type", multiple = true)
    @NotEmpty(message = "支付方式不能为空")
    @ApiModelProperty("支付方式, 多种用逗号分隔")
    private String payModes;
}
