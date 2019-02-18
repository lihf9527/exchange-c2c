package com.exchange.c2c.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exchange.c2c.common.exception.BizException;
import com.exchange.c2c.common.util.Assert;
import com.exchange.c2c.entity.Account;
import com.exchange.c2c.mapper.AccountMapper;
import com.exchange.c2c.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public Account findOne(Long userId, Integer currencyId) {
        QueryWrapper<Account> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("currency_id", currencyId);
        return Optional.ofNullable(accountMapper.selectOne(wrapper)).orElseThrow(() -> new BizException("账户不存在"));
    }

    @Override
    @Transactional
    public void freeze(Integer currencyId, Long userId, BigDecimal amount) {
        Account account = findOne(userId, currencyId);
        BigDecimal availableAmount = account.getAvailableAmount().subtract(amount);
        Assert.isEquals(-1, availableAmount.signum(), "账户余额不足");
        boolean result = updateAmount(account.getAccountId(), account.getVersion(), availableAmount, account.getFrozenAmount().add(amount));
        Assert.isTrue(result, "冻结失败,请稍候重试");
    }

    @Override
    @Transactional
    public void unfreeze(Integer currencyId, Long userId, BigDecimal amount) {
        Account account = findOne(userId, currencyId);
        BigDecimal frozenAmount = account.getFrozenAmount().subtract(amount);
        Assert.isEquals(-1, frozenAmount.signum(), "冻结余额不足");
        boolean result = updateAmount(account.getAccountId(), account.getVersion(), account.getAvailableAmount().add(amount), frozenAmount);
        Assert.isTrue(result, "解冻失败,请稍候重试");
    }

    @Override
    @Transactional
    public void transfer(Integer currencyId, Long sourceUserId, Long targetUserId, BigDecimal amount) {
        Account sourceAccount = findOne(sourceUserId, currencyId);
        Account targetAccount = findOne(targetUserId, currencyId);
        BigDecimal frozenAmount = sourceAccount.getFrozenAmount().subtract(amount);
        Assert.isEquals(-1, frozenAmount, "冻结余额不足");
        boolean result1 = updateAmount(sourceAccount.getAccountId(), sourceAccount.getVersion(), null, frozenAmount);
        boolean result2 = updateAmount(targetAccount.getAccountId(), targetAccount.getVersion(), targetAccount.getAvailableAmount().add(amount), null);
        Assert.isTrue(result1 && result2, "交易失败,请稍候重试");
    }

    private boolean updateAmount(Long accountId, Long version, BigDecimal availableAmount, BigDecimal frozenAmount) {
        Account temp = new Account();
        temp.setAvailableAmount(availableAmount);
        temp.setFrozenAmount(frozenAmount);
        temp.setVersion(version + 1);

        QueryWrapper<Account> wrapper = new QueryWrapper<>();
        wrapper.eq("account_id", accountId);
        wrapper.eq("version", version);

        return accountMapper.update(temp, wrapper) > 0;
    }
}
