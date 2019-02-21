package com.exchange.c2c.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单明细表 存广告和支付方式的快照信息
 */
@Data
@TableName("c2c_order_detail")
public class OrderDetail {
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
     * 广告编号
     */
    private String adNo;

    /**
     * 广告类型 1购买 2出售
     */
    private Integer adType;

    /**
     * 总数量
     */
    private BigDecimal adTotalQuantity;

    /**
     * 剩余数量
     */
    private BigDecimal adSurplusQuantity;

    /**
     * 数量单笔限额 最小值
     */
    private BigDecimal adQuantityLimitMin;

    /**
     * 数量单笔限额 最大值
     */
    private BigDecimal adQuantityLimitMax;

    /**
     * 金额单笔限额 最小值
     */
    private BigDecimal adMoneyLimitMin;

    /**
     * 金额单笔限额 最大值
     */
    private BigDecimal adMoneyLimitMax;

    /**
     * 支付方式 1支付宝 2微信 3银行卡
     */
    private String adPayModes;

    /**
     * 支付方式ID, 多个用逗号分隔
     */
    private String adPayModeIds;

    /**
     * 广告备注
     */
    private String adRemark;

    /**
     * 卖方账户类型 1支付宝 2微信 3银行卡
     */
    private String sellerAccountType;

    /**
     * 卖方账户名
     */
    private String sellerAccountName;

    /**
     * 卖方账号
     */
    private String sellerAccountNumber;

    /**
     * 卖方二维码
     */
    private String sellerQrCode;

    /**
     * 卖方开户银行
     */
    private String sellerBank;

    /**
     * 卖方开户支行
     */
    private String sellerBranchBank;
}
