package com.exchange.c2c.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 广告表
 */
@Data
@TableName("c2c_advert")
public class Advert {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 广告编号
     */
    private String adNo;

    /**
     * 广告类型 1购买 2出售
     */
    private Integer type;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 总数量
     */
    private BigDecimal totalQuantity;

    /**
     * 剩余数量
     */
    private BigDecimal surplusQuantity;

    /**
     * 币种编号
     */
    private String currencyCode;

    /**
     * 法币编号
     */
    private String legalCurrencyCode;

    /**
     * 数量单笔限额 最小值
     */
    private BigDecimal quantityLimitMin;

    /**
     * 数量单笔限额 最大值
     */
    private BigDecimal quantityLimitMax;

    /**
     * 金额单笔限额 最小值
     */
    private BigDecimal moneyLimitMin;

    /**
     * 金额单笔限额 最大值
     */
    private BigDecimal moneyLimitMax;

    /**
     * 支付方式 1支付宝 2微信 3银行卡
     */
    private String payModes;

    /**
     * 支付方式ID
     */
    private String payModeIds;

    /**
     * 创建人ID
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人ID
     */
    private Long updateBy;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 广告状态 1上架 0下架
     */
    private Integer status;

    /**
     * 广告备注
     */
    private String remark;

    /**
     * 乐观锁版本号
     */
    private Long version;
}
