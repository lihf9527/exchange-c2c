package com.exchange.c2c.model;

import com.exchange.c2c.common.annotation.EnumValue;
import com.exchange.c2c.common.page.PageForm;
import com.exchange.c2c.enums.AccountTypeEnum;
import com.exchange.c2c.enums.PayModeStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class PayModeForm extends PageForm {
    @EnumValue(message = "账号类型枚举值不正确", enumClass = AccountTypeEnum.class, nullable = true)
    @ApiModelProperty("账号类型: 1 - 支付宝, 2 - 微信, 3 - 银行卡")
    private Integer accountType;

    @EnumValue(message = "状态枚举值不正确", enumClass = PayModeStatusEnum.class, nullable = true)
    @ApiModelProperty("状态 0禁用1启用")
    private String status;
}
