package com.exchange.c2c.model;

import com.exchange.c2c.common.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel
public class PayModeModel {
    @ApiModelProperty("支付方式ID")
    private Integer id;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("姓名")
    private String userName;

    @ApiModelProperty("账户类型 1支付宝 2微信 3银行卡")
    private Integer accountType;

    @ApiModelProperty("微信/支付宝/银行卡 账号")
    private String account;

    @ApiModelProperty("微信/支付宝 二维码")
    private String qrCode;

    @ApiModelProperty("开户银行")
    private String bank;

    @ApiModelProperty("开户支行")
    private String branchBank;

    @JsonFormat(pattern = Constant.DATE_TIME, timezone = Constant.TIMEZONE_CN)
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @JsonFormat(pattern = Constant.DATE_TIME, timezone = Constant.TIMEZONE_CN)
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("状态 0禁用 1启用")
    private String status;
}
