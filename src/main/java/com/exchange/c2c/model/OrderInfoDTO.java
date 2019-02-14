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
public class OrderInfoDTO {
    @ApiModelProperty("订单ID")
    private Integer id;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("交易类型 1购买 2出售")
    private String type;

    @ApiModelProperty("买方名称")
    private String buyerName;

    @ApiModelProperty("卖方名称")
    private String sellerName;

    @ApiModelProperty("单价")
    private BigDecimal price;

    @ApiModelProperty("数量")
    private BigDecimal quantity;

    @ApiModelProperty("总价")
    private BigDecimal totalPrice;

    @ApiModelProperty("订单状态 1待支付 2待确认 3已取消 4申诉中 5已完成")
    private Integer status;

    @JsonFormat(pattern = Constant.DATE_TIME, timezone = Constant.TIMEZONE_CN)
    @ApiModelProperty("下单时间")
    private LocalDateTime createTime;

    @JsonFormat(pattern = Constant.DATE_TIME, timezone = Constant.TIMEZONE_CN)
    @ApiModelProperty("确认付款时间")
    private LocalDateTime payTime;

    @JsonFormat(pattern = Constant.DATE_TIME, timezone = Constant.TIMEZONE_CN)
    @ApiModelProperty("确认收款时间")
    private LocalDateTime finishTime;

    @ApiModelProperty("卖家收款方式 1支付宝 2微信 3银行卡")
    private String sellerPayMode;

    @ApiModelProperty("买家支付方式 1支付宝 2微信 3银行卡")
    private String buyerPayMode;

    @ApiModelProperty("买家支付凭证 URL 多个用逗号分隔")
    private String buyerPayVoucher;

    @ApiModelProperty("卖方收款账户类型 1支付宝 2微信 3银行卡")
    private Integer sellerAccountType;

    @ApiModelProperty("卖方收款账户名")
    private String sellerAccountName;

    @ApiModelProperty("卖方收款账号")
    private String sellerAccountNumber;

    @ApiModelProperty("卖方 支付宝/微信 收款二维码")
    private String sellerQrCode;

    @ApiModelProperty("卖方收款账户开户银行")
    private String sellerBank;

    @ApiModelProperty("卖方收款账户开户支行")
    private String sellerBranchBank;
}
