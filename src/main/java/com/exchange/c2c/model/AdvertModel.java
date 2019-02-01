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
public class AdvertModel {
    @ApiModelProperty("广告ID")
    private Integer id;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("广告编号")
    private String advNo;

    @ApiModelProperty("广告类型: 1购买, 2出售")
    private String type;

    @ApiModelProperty("单价")
    private BigDecimal price;

    @ApiModelProperty("单笔限额 下限")
    private Integer min_value;

    @ApiModelProperty("单笔限额 上限")
    private Integer max_value;

    @ApiModelProperty("支付方式 1支付宝, 2微信, 3银行卡")
    private String pay_modes;

    @ApiModelProperty("支付方式id,多个用逗号分隔")
    private String pay_mode_ids;

    @JsonFormat(pattern = Constant.DATE_TIME, timezone = Constant.TIMEZONE_CN)
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @JsonFormat(pattern = Constant.DATE_TIME, timezone = Constant.TIMEZONE_CN)
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("广告状态 1上架 0 下架")
    private String status;
}
