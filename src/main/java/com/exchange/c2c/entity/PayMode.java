package com.exchange.c2c.entity;

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
    private Integer id;

    /**
     * 账号类型 1支付宝 2微信 3银行卡
     */
    private Integer accountType;

    /**
     * 账户名
     */
    private String accountName;

    /**
     * 账号
     */
    private String accountNumber;

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
     * 状态 0禁用 1启用
     */
    private Integer status;

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
}
