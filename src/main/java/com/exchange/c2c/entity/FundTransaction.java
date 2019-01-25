package com.exchange.c2c.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 交易流水表
 */
@Data
@TableName("user_fund_transactions")
public class FundTransaction {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long fundTxId;

    /**
     * 跟本次资金变动相关的之前资金变动记录的ID。
     */
    private Long startFundTxId;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建日期
     */
    private LocalDateTime createdDate;

    /**
     * 账户ID
     */
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
     * 资金变动之前余额
     */
    private BigDecimal availableBefore;

    /**
     * 资金变动之后余额
     */
    private BigDecimal availableAfter;

    /**
     * 资金变动之前冻结余额
     */
    private BigDecimal frozenBefore;

    /**
     * 资金变动之后冻结余额
     */
    private BigDecimal frozenAfter;

    /**
     * 资金变动原因
     */
    private String reasonCode;

    /**
     * 资金变动类型
     */
    private String fundTxType;

    /**
     * 变动的冻结资金数量
     */
    private BigDecimal frozenAmount;

    /**
     * 版本号
     */
    private Long version;

    /**
     * 变动的资金数量
     */
    private BigDecimal amount;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 交易账户ID
     */
    private Long accountTxId;

    /**
     * 修改日期
     */
    private LocalDateTime updatedDate;
}