package com.exchange.c2c.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

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
    private String advNo;

    /**
     * 广告类型 1购买 2出售
     */
    private Integer advType;

    /**
     * 广告创建者ID
     */
    private Long advCreateBy;

    /**
     * 广告单笔限额 下限
     */
    private Integer advMinValue;

    /**
     * 广告单笔限额 上限
     */
    private Integer advMaxValue;

    /**
     * 支付方式 1支付宝 2微信 3银行卡
     */
    private String advPayModes;

    /**
     * 支付方式ID, 多个用逗号分隔
     */
    private String advPayModeIds;

    /**
     * 卖方账户类型 1支付宝 2微信 3银行卡
     */
    private Integer sellerAccountType;

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
