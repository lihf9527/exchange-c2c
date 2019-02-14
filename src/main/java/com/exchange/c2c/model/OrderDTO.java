package com.exchange.c2c.model;

import com.exchange.c2c.common.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel
public class OrderDTO {
    @ApiModelProperty("订单ID")
    private Integer id;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("交易类型 1购买 2出售")
    private Integer type;

    @ApiModelProperty("总价")
    private BigDecimal totalPrice;

    @ApiModelProperty("单价")
    private BigDecimal price;

    @ApiModelProperty("订单状态 1待支付 2待确认 3已取消 4申诉中 5已完成")
    private Integer status;

    @ApiModelProperty("交易对象")
    private String targetName;

    @JsonFormat(pattern = Constant.DATE_TIME, timezone = Constant.TIMEZONE_CN)
    @ApiModelProperty("下单时间")
    private LocalDateTime createTime;
}
