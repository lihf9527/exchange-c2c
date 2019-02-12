package com.exchange.c2c.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.exchange.c2c.entity.Advert;
import com.exchange.c2c.model.MarketAdvertForm;
import com.exchange.c2c.model.MyAdsForm;

public interface AdvertService {
    Advert findById(Integer id);

    void save(Advert advert);

    IPage<Advert> findAll(MyAdsForm form);

    IPage<Advert> findAll(MarketAdvertForm form);
}
