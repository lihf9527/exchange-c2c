package com.exchange.c2c.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 广告表
 */
@Data
@TableName("c2c_advertisement")
public class Advertisement {
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
     * 广告编号
     */
    private String advNo;

    /**
     * 类型: 1购买, 2出售
     */
    private String type;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 单笔限额 下限
     */
    private Integer minValue;

    /**
     * 单笔限额 上限
     */
    private Integer maxValue;

    /**
     * 支付方式 1支付宝, 2微信, 3银行卡
     */
    private String payModes;

    /**
     * 支付方式id,多个用逗号分隔
     */
    private String payModeIds;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 状态 1上架 0 下架
     */
    private String status;
}
