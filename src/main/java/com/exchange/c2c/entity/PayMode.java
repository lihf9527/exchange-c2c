package com.exchange.c2c.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 支付方式表
 */
@Data
@TableName("c2c_pay_mode")
public class PayMode {
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
     * 姓名
     */
    private String userName;

    /**
     * 账户类型 1支付宝 2微信 3银行卡
     */
    private Integer accountType;

    /**
     * 账号/银行卡号
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
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 状态 0禁用1启用
     */
    private String status;
}
