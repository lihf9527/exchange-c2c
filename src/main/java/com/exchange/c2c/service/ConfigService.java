package com.exchange.c2c.service;

import com.exchange.c2c.entity.Config;

import java.util.List;

public interface ConfigService {
    List<Config> findAll(String code);
}
