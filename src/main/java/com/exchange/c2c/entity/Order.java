package com.exchange.c2c.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单表
 */
@Data
@TableName("c2c_order")
public class Order {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 数量
     */
    private BigDecimal quantity;

    /**
     * 总价
     */
    private BigDecimal totalPrice;

    /**
     * 币种编号
     */
    private String currencyCode;

    /**
     * 法币编号
     */
    private String legalCurrencyCode;

    /**
     * 支付方式 1支付宝 2微信 3银行卡
     */
    private String payMode;

    /**
     * 买家转账的支付方式 1支付宝 2微信 3银行卡
     */
    private String transferPayMode;

    /**
     * 买家转账凭证
     */
    private String transferVoucher;

    /**
     * 订单状态 1待支付 2待确认 3已取消 4申诉中 5已完成
     */
    private Integer status;

    /**
     * 买家ID
     */
    private Long buyerId;

    /**
     * 卖家ID
     */
    private Long sellerId;

    /**
     * 订单发起人ID
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 付款时间
     */
    private LocalDateTime payTime;

    /**
     * 取消时间
     */
    private LocalDateTime cancelTime;

    /**
     * 申诉时间
     */
    private LocalDateTime appealTime;

    /**
     * 完成时间
     */
    private LocalDateTime finishTime;
}
