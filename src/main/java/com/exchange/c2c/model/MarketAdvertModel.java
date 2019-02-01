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
public class MarketAdvertModel {
    @ApiModelProperty("商家名称")
    private String sellerName;

    @ApiModelProperty("商家成交笔数")
    private Long count;

    @ApiModelProperty("单价")
    private BigDecimal price;

    @ApiModelProperty("单笔限额 下限")
    private Integer min_value;

    @ApiModelProperty("单笔限额 上限")
    private Integer max_value;

    @ApiModelProperty("支付方式 1支付宝, 2微信, 3银行卡")
    private String pay_modes;

    @ApiModelProperty("广告类型 1购买 2出售")
    private String type;

    @JsonFormat(pattern = Constant.DATE_TIME, timezone = Constant.TIMEZONE_CN)
    @ApiModelProperty("发布时间")
    private LocalDateTime createTime;
}
