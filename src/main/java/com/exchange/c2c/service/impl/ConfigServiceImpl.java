package com.exchange.c2c.service.impl;

import com.exchange.c2c.entity.Config;
import com.exchange.c2c.mapper.ConfigMapper;
import com.exchange.c2c.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigServiceImpl implements ConfigService {
    @Autowired
    private ConfigMapper configMapper;

    @Override
    public List<Config> findAll(String code) {
        return configMapper.findAll(code);
    }
}
