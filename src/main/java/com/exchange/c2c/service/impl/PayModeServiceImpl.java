package com.exchange.c2c.service.impl;

import com.exchange.c2c.common.exception.BizException;
import com.exchange.c2c.common.util.Assert;
import com.exchange.c2c.entity.PayMode;
import com.exchange.c2c.enums.PayModeStatusEnum;
import com.exchange.c2c.mapper.PayModeMapper;
import com.exchange.c2c.service.PayModeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            PayMode old = findById(payMode.getId());
            Assert.isEquals(old.getUserId(), payMode.getUserId(), "非法操作");
            payModeMapper.updateById(payMode);
        }
    }

    @Override
    @Transactional
    public void enable(Integer id) {

    }

    @Override
    @Transactional
    public void disable(Integer id) {
        PayMode temp = new PayMode();
        temp.setStatus(PayModeStatusEnum.DISABLE.getValue());
        payModeMapper.updateById(temp);
    }
}
