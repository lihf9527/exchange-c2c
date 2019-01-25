package com.exchange.c2c.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 账户表
 */
@Data
@TableName("user_accounts")
public class Account {
    /**
     * 账户ID
     */
    @TableId(type = IdType.AUTO)
    private Long accountId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 币种ID
     */
    private Integer currencyId;

    /**
     * 可用金额
     */
    private BigDecimal availableAmount;

    /**
     * 冻结金额
     */
    private BigDecimal frozenAmount;

    /**
     * 区块地址
     */
    private String blockchainAddress;

    /**
     * 钱包密码
     */
    private String walletPassword;

    /**
     * 钱包金额
     */
    private BigDecimal walletAmount;

    /**
     * 版本
     */
    private Long version;

    /**
     * 创建时间
     */
    private LocalDateTime createdDate;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 修改时间
     */
    private LocalDateTime updatedDate;

    /**
     * 修改人
     */
    private String updatedBy;

    private String walletSpender;

    private Integer currencyStatus;
}
