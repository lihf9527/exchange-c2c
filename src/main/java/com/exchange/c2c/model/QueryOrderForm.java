package com.exchange.c2c.model;

import com.exchange.c2c.common.annotation.EnumValue;
import com.exchange.c2c.common.page.PageForm;
import com.exchange.c2c.enums.CurrentOrderStatusEnum;
import com.exchange.c2c.enums.HistoryOrderStatusEnum;
import com.exchange.c2c.enums.TradingTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class QueryOrderForm extends PageForm {
    @ApiModelProperty("订单编号")
    private String orderNo;

    @EnumValue(message = "交易类型枚举值不正确", enumClass = TradingTypeEnum.class, nullable = true)
    @ApiModelProperty("交易类型 1购买 2出售")
    private Integer type;

    @EnumValue(message = "订单状态枚举值不正确", enumClass = CurrentOrderStatusEnum.class, nullable = true, groups = CurrentOrderStatusEnum.class)
    @EnumValue(message = "订单状态枚举值不正确", enumClass = HistoryOrderStatusEnum.class, nullable = true, groups = HistoryOrderStatusEnum.class)
    @ApiModelProperty("订单状态 1待支付 2待确认 3已取消 4申诉中 5已完成 当前订单(1,2,4) 历史订单(3,5)")
    private Integer status;
}
