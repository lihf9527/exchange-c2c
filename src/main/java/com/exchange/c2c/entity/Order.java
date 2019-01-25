package com.exchange.c2c.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

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
     * 用户ID
     */
    private Long userId;

    /**
     * 交易对象ID
     */
    private Long targetId;

    /**
     * 广告ID
     */
    private Integer advId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 订单类型 1购买2出售
     */
    private String orderType;

    /**
     * 支付方式 1支付宝 2微信 3银行卡
     */
    private String payMode;

    /**
     * 转账方式 1支付宝 2微信 3银行卡
     */
    private String transferMode;

    /**
     * 转账凭证url
     */
    private String transferVoucher;

    /**
     * 账户名
     */
    private String accountName;

    /**
     * 账号
     */
    private String account;

    /**
     * 二维码
     */
    private String qrCode;

    /**
     * 开户银行
     */
    private String bank;

    /**
     * 开户支行
     */
    private String branchBank;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 数量
     */
    private BigDecimal amount;

    /**
     * 总价
     */
    private BigDecimal totalPrice;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 转账时间
     */
    private Date transferTime;

    /**
     * 取消时间
     */
    private Date cancelTime;

    /**
     * 申诉时间
     */
    private Date appealTime;

    /**
     * 完成时间
     */
    private Date finishTime;

    /**
     * 状态 1待付款 2待确认 3已取消 4申诉中 5已完成
     */
    private Integer status;
}
