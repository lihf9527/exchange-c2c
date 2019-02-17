package com.exchange.c2c.service;

import com.exchange.c2c.entity.Account;

import java.math.BigDecimal;

public interface AccountService {
    Account findOne(Long userId, Integer currencyId);

    /**
     * 冻结
     *
     * @param currencyId 币种ID
     * @param userId     用户ID
     * @param amount     冻结金额
     */
    void freeze(Integer currencyId, Long userId, BigDecimal amount);

    /**
     * 解冻
     *
     * @param currencyId 币种ID
     * @param userId     用户ID
     * @param amount     解冻金额
     */
    void unfreeze(Integer currencyId, Long userId, BigDecimal amount);

    /**
     * 转账
     *
     * @param currencyId   币种ID
     * @param sourceUserId 源用户ID
     * @param targetUserId 目标用户ID
     * @param amount       转账金额
     */
    void transfer(Integer currencyId, Long sourceUserId, Long targetUserId, BigDecimal amount);
}
