package com.exchange.c2c.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.exchange.c2c.entity.PayMode;
import com.exchange.c2c.model.PayModeForm;

public interface PayModeService {
    PayMode findById(Integer id);

    void save(PayMode payMode);

    void enable(Integer id);

    void disable(Integer id);

    void disableByAccountType(Integer accountType);

    IPage<PayMode> findAll(PayModeForm form);
}
