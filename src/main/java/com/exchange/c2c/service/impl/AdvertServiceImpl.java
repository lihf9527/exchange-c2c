package com.exchange.c2c.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exchange.c2c.common.exception.BizException;
import com.exchange.c2c.common.util.Assert;
import com.exchange.c2c.common.util.WebUtils;
import com.exchange.c2c.entity.Advert;
import com.exchange.c2c.enums.AdvertStatusEnum;
import com.exchange.c2c.enums.AdvertTypeEnum;
import com.exchange.c2c.mapper.AdvertMapper;
import com.exchange.c2c.mapper.CurrencyMapper;
import com.exchange.c2c.model.MarketAdvertForm;
import com.exchange.c2c.model.MyAdsForm;
import com.exchange.c2c.service.AccountService;
import com.exchange.c2c.service.AdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

@Service
public class AdvertServiceImpl implements AdvertService {
    @Autowired
    private AdvertMapper advertMapper;
    @Autowired
    private CurrencyMapper currencyMapper;

    @Autowired
    private AccountService accountService;

    @Override
    public Advert findById(Integer id) {
        return Optional.ofNullable(advertMapper.selectById(id)).orElseThrow(() -> new BizException("广告不存在"));
    }

    @Override
    public Advert findByAdNo(String adNo) {
        QueryWrapper<Advert> wrapper = new QueryWrapper<>();
        wrapper.eq("ad_no", adNo);
        return Optional.ofNullable(advertMapper.selectOne(wrapper)).orElseThrow(() -> new BizException("广告不存在"));
    }

    @Override
    public void create(Advert advert) {
        advertMapper.insert(advert);
    }

    @Override
    @Transactional
    public void update(Advert advert) {
        update(advert, advert.getId(), advert.getVersion());
    }

    @Override
    @Transactional
    public void decr(String adNo, BigDecimal quantity) {
        Advert oldAdvert = findByAdNo(adNo);
        Assert.isEquals(AdvertStatusEnum.ENABLE.getValue(), oldAdvert.getStatus(), "广告已下架");

        BigDecimal surplusQuantity = oldAdvert.getSurplusQuantity().subtract(quantity);
        Assert.isFalse(surplusQuantity.signum() < 0, "广告剩余数量不足");

        Advert advert = new Advert();
        advert.setSurplusQuantity(surplusQuantity);
        update(advert, oldAdvert.getId(), oldAdvert.getVersion());

        if (surplusQuantity.signum() == 0) {
            disable(oldAdvert.getId());
        }
    }

    @Override
    @Transactional
    public void enable(Integer id) {
        Advert oldAdvert = findById(id);
        Assert.isEquals(AdvertStatusEnum.DISABLE.getValue(), oldAdvert.getStatus(), "广告已上架");
        if (Objects.equals(AdvertTypeEnum.SELL.getValue(), oldAdvert.getType())) {// 如果是出售,冻结资金
            Integer currencyId = currencyMapper.findCurrencyIdByCode(oldAdvert.getCurrencyCode());
            accountService.freeze(currencyId, oldAdvert.getCreateBy(), oldAdvert.getTotalQuantity());
        }
        // 修改广告状态为已上架
        Advert advert = new Advert();
        advert.setSurplusQuantity(advert.getTotalQuantity());
        advert.setStatus(AdvertStatusEnum.ENABLE.getValue());
        update(advert, oldAdvert.getId(), oldAdvert.getVersion());
    }

    @Override
    @Transactional
    public void disable(Integer id) {
        Advert oldAdvert = findById(id);
        Assert.isEquals(AdvertStatusEnum.ENABLE.getValue(), oldAdvert.getStatus(), "广告已下架");
        if (Objects.equals(AdvertTypeEnum.SELL.getValue(), oldAdvert.getType())) {// 如果是出售,解冻剩余资金
            Integer currencyId = currencyMapper.findCurrencyIdByCode(oldAdvert.getCurrencyCode());
            accountService.unfreeze(currencyId, oldAdvert.getCreateBy(), oldAdvert.getSurplusQuantity());
        }
        // 修改广告状态为已下架
        Advert advert = new Advert();
        advert.setSurplusQuantity(BigDecimal.ZERO);
        advert.setStatus(AdvertStatusEnum.DISABLE.getValue());
        update(advert, oldAdvert.getId(), oldAdvert.getVersion());
    }

    private void update(Advert advert, Integer id, Long version) {
        advert.setVersion(version + 1);
        QueryWrapper<Advert> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        wrapper.eq("version", version);
        int rows = advertMapper.update(advert, wrapper);
        Assert.isTrue(rows > 0, "操作失败,请稍候再试");
    }

    @Override
    public IPage<Advert> findAll(MyAdsForm form) {
        QueryWrapper<Advert> wrapper = new QueryWrapper<>();
        wrapper.eq("create_by", WebUtils.getUserId());
        if (!StringUtils.isEmpty(form.getType())) {
            wrapper.eq("type", form.getType());
        }
        if (!StringUtils.isEmpty(form.getStatus())) {
            wrapper.eq("status", form.getStatus());
        }
        wrapper.orderByDesc("create_time", "status");

        return advertMapper.selectPage(new Page<>(form.getPageIndex(), form.getPageSize()), wrapper);
    }

    @Override
    public IPage<Advert> findAll(MarketAdvertForm form) {
        QueryWrapper<Advert> wrapper = new QueryWrapper<>();
        wrapper.eq("status", AdvertStatusEnum.ENABLE.getValue());
        wrapper.eq("type", form.getType());
        if (!StringUtils.isEmpty(form.getCurrencyCode())) {
            wrapper.eq("currency_code", form.getCurrencyCode());
        }
        if (!StringUtils.isEmpty(form.getLegalCurrencyCode())) {
            wrapper.eq("legal_currency_code", form.getLegalCurrencyCode());
        }
        if (!StringUtils.isEmpty(form.getPayMode())) {
            wrapper.like("pay_modes", form.getPayMode());
        }

        return advertMapper.selectPage(new Page<>(form.getPageIndex(), form.getPageSize()), wrapper);
    }
}
