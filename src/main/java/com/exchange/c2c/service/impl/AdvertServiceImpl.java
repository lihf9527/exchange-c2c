package com.exchange.c2c.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exchange.c2c.common.exception.BizException;
import com.exchange.c2c.common.util.WebUtils;
import com.exchange.c2c.entity.Advert;
import com.exchange.c2c.enums.AdvertStatusEnum;
import com.exchange.c2c.mapper.AdvertMapper;
import com.exchange.c2c.model.MarketAdvertForm;
import com.exchange.c2c.model.MyAdsForm;
import com.exchange.c2c.service.AdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Optional;

@Service
public class AdvertServiceImpl implements AdvertService {
    @Autowired
    private AdvertMapper advertMapper;

    @Override
    public Advert findById(Integer id) {
        return Optional.ofNullable(advertMapper.selectById(id)).orElseThrow(() -> new BizException("广告不存在"));
    }

    @Override
    public void save(Advert advert) {
        if (Objects.isNull(advert.getId())) {
            advertMapper.insert(advert);
        } else {
            advertMapper.updateById(advert);
        }
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

        return advertMapper.selectPage(new Page<>(form.getPageIndex(), form.getPageSize()), wrapper);
    }

    @Override
    public IPage<Advert> findAll(MarketAdvertForm form) {
        QueryWrapper<Advert> wrapper = new QueryWrapper<>();
        wrapper.eq("status", AdvertStatusEnum.ENABLE.getValue());
        wrapper.eq("type", form.getType());

        return advertMapper.selectPage(new Page<>(form.getPageIndex(), form.getPageSize()), wrapper);
    }
}
