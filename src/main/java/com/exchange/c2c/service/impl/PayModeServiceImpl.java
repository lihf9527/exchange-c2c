package com.exchange.c2c.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exchange.c2c.common.exception.BizException;
import com.exchange.c2c.common.util.WebUtils;
import com.exchange.c2c.entity.PayMode;
import com.exchange.c2c.enums.PayModeStatusEnum;
import com.exchange.c2c.mapper.PayModeMapper;
import com.exchange.c2c.model.PayModeForm;
import com.exchange.c2c.service.PayModeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PayModeServiceImpl implements PayModeService {
    @Autowired
    private PayModeMapper payModeMapper;

    @Override
    public PayMode findById(Integer id) {
        return Optional.ofNullable(payModeMapper.selectById(id)).orElseThrow(() -> new BizException("支付方式不存在"));
    }

    @Override
    @Transactional
    public void save(PayMode payMode) {
        if (Objects.isNull(payMode.getId())) {
            payModeMapper.insert(payMode);
        } else {
            payModeMapper.updateById(payMode);
        }
    }

    @Override
    @Transactional
    public void enable(Integer id) {
        PayMode payMode = findById(id);
        disable(payMode.getCreateBy(), payMode.getAccountType());

        PayMode temp = new PayMode();
        temp.setId(id);
        temp.setStatus(PayModeStatusEnum.ENABLE.getValue());
        payModeMapper.updateById(temp);
    }

    @Override
    @Transactional
    public void disable(Integer id) {
        PayMode temp = new PayMode();
        temp.setId(id);
        temp.setStatus(PayModeStatusEnum.DISABLE.getValue());
        payModeMapper.updateById(temp);
    }

    @Override
    @Transactional
    public void disable(Long userId, String accountType) {
        PayMode temp = new PayMode();
        temp.setStatus(PayModeStatusEnum.DISABLE.getValue());

        UpdateWrapper<PayMode> wrapper = new UpdateWrapper<>();
        wrapper.eq("create_by", userId);
        wrapper.eq("account_type", accountType);

        payModeMapper.update(temp, wrapper);
    }

    @Override
    public IPage<PayMode> findAll(PayModeForm form) {
        QueryWrapper<PayMode> wrapper = new QueryWrapper<>();
        wrapper.eq("create_by", WebUtils.getUserId());
        if (Objects.nonNull(form.getAccountType())) {
            wrapper.eq("account_type", form.getAccountType());
        }
        if (!StringUtils.isEmpty(form.getStatus())) {
            wrapper.eq("status", form.getStatus());
        }
        wrapper.orderByDesc("status");
        return payModeMapper.selectPage(new Page<>(form.getPageIndex(), form.getPageSize()), wrapper);
    }

    @Override
    public List<PayMode> findEnabled(Long userId, String... accountTypes) {
        QueryWrapper<PayMode> wrapper = new QueryWrapper<>();
        wrapper.eq("create_by", userId);
        wrapper.eq("status", PayModeStatusEnum.ENABLE.getValue());
        if (Objects.nonNull(accountTypes)) {
            wrapper.in("account_type", Arrays.asList(accountTypes));
        }
        return payModeMapper.selectList(wrapper);
    }
}
