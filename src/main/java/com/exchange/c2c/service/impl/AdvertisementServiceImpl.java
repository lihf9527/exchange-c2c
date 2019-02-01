package com.exchange.c2c.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exchange.c2c.common.exception.BizException;
import com.exchange.c2c.common.util.WebUtils;
import com.exchange.c2c.entity.Advertisement;
import com.exchange.c2c.enums.AdvertStatusEnum;
import com.exchange.c2c.mapper.AdvertisementMapper;
import com.exchange.c2c.model.MarketAdvertForm;
import com.exchange.c2c.model.MyAdsForm;
import com.exchange.c2c.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Optional;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {
    @Autowired
    private AdvertisementMapper advertisementMapper;

    @Override
    public Advertisement findById(Integer id) {
        return Optional.ofNullable(advertisementMapper.selectById(id)).orElseThrow(() -> new BizException("广告不存在"));
    }

    @Override
    public void save(Advertisement advertisement) {
        if (Objects.isNull(advertisement.getId())) {
            advertisementMapper.insert(advertisement);
        } else {
            advertisementMapper.updateById(advertisement);
        }
    }

    @Override
    public IPage<Advertisement> findAll(MyAdsForm form) {
        QueryWrapper<Advertisement> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", WebUtils.getUserId());
        if (!StringUtils.isEmpty(form.getType())) {
            wrapper.eq("type", form.getType());
        }
        if (!StringUtils.isEmpty(form.getStatus())) {
            wrapper.eq("status", form.getStatus());
        }

        return advertisementMapper.selectPage(new Page<>(form.getPageIndex(), form.getPageSize()), wrapper);
    }

    @Override
    public IPage<Advertisement> findAll(MarketAdvertForm form) {
        QueryWrapper<Advertisement> wrapper = new QueryWrapper<>();
        wrapper.eq("status", AdvertStatusEnum.ENABLE.getValue());
        wrapper.eq("type", form.getType());

        return advertisementMapper.selectPage(new Page<>(form.getPageIndex(), form.getPageSize()), wrapper);
    }
}
