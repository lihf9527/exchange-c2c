package com.exchange.c2c.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.exchange.c2c.entity.Advertisement;
import com.exchange.c2c.model.MarketAdvertForm;
import com.exchange.c2c.model.MyAdsForm;

public interface AdvertisementService {
    Advertisement findById(Integer id);

    void save(Advertisement advertisement);

    IPage<Advertisement> findAll(MyAdsForm form);

    IPage<Advertisement> findAll(MarketAdvertForm form);
}
