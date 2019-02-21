package com.exchange.c2c.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.exchange.c2c.entity.PayMode;
import com.exchange.c2c.model.PayModeForm;

import java.util.List;

public interface PayModeService {
    PayMode findById(Integer id);

    void save(PayMode payMode);

    void enable(Integer id);

    void disable(Integer id);

    void disable(Long userId, String accountType);

    IPage<PayMode> findAll(PayModeForm form);

    /**
     * 查询用户已启用的支付方式
     *
     * @param userId       用户ID
     * @param accountTypes {@link com.exchange.c2c.enums.AccountTypeEnum}
     * @return 已启用的支付方式
     */
    List<PayMode> findEnabled(Long userId, String... accountTypes);
}
