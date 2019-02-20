package com.exchange.c2c.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.exchange.c2c.entity.Advert;
import com.exchange.c2c.model.MarketAdvertForm;
import com.exchange.c2c.model.MyAdsForm;

import java.math.BigDecimal;

public interface AdvertService {
    Advert findById(Integer id);

    Advert findByAdNo(String adNo);

    void save(Advert advert);

    /**
     * 减少广告剩余数量
     */
    void decr(String adNo, BigDecimal quantity);

    /**
     * 上架广告
     */
    void enable(Integer id);

    /**
     * 下架广告
     */
    void disable(Integer id);

    IPage<Advert> findAll(MyAdsForm form);

    IPage<Advert> findAll(MarketAdvertForm form);
}
