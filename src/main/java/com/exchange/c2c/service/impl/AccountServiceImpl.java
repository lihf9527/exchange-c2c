package com.exchange.c2c.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.exchange.c2c.common.exception.BizException;
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
        Account temp = new Account();
        temp.setAccountId(account.getAccountId());
        temp.setAvailableAmount(account.getAvailableAmount().subtract(amount));
        temp.setFrozenAmount(account.getFrozenAmount().add(amount));
        UpdateWrapper<Account> wrapper = new UpdateWrapper<>();
        wrapper.set("", null);
        wrapper.eq("", null);
        accountMapper.update(temp, null);
    }

    @Override
    @Transactional
    public void unfreeze(Integer currencyId, Long userId, BigDecimal amount) {

    }

    @Override
    @Transactional
    public void transfer(Integer currencyId, Long sourceUserId, Long targetUserId, BigDecimal amount) {

    }
}
