package com.exchange.c2c.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel
public class MarketAdvertDTO {
    @ApiModelProperty("广告编号")
    private String adNo;

    @ApiModelProperty("商家名称")
    private String sellerName;

    @ApiModelProperty("成单数")
    private Long count;

    @ApiModelProperty("交易笔数")
    private Long totalCount;

    @ApiModelProperty("完成率")
    private String ratio;

    @ApiModelProperty("币种编号")
    private String currencyCode;

    @ApiModelProperty("法币编号")
    private String legalCurrencyCode;

    @ApiModelProperty("单价")
    private BigDecimal price;

    @ApiModelProperty("总数量")
    private BigDecimal totalQuantity;

    @ApiModelProperty("剩余数量")
    private BigDecimal surplusQuantity;

    @ApiModelProperty("数量单笔限额 最小值")
    private BigDecimal quantityLimitMin;

    @ApiModelProperty("数量单笔限额 最大值")
    private BigDecimal quantityLimitMax;

    @ApiModelProperty("金额单笔限额 最小值")
    private BigDecimal moneyLimitMin;

    @ApiModelProperty("金额单笔限额 最大值")
    private BigDecimal moneyLimitMax;

    @ApiModelProperty("支付方式 1支付宝, 2微信, 3银行卡")
    private String payModes;

    @ApiModelProperty("商家备注")
    private String remark;

    @ApiModelProperty("广告状态 1上架 0下架")
    private Integer status;

    @ApiModelProperty("广告类型 1购买 2出售")
    private Integer type;
}
