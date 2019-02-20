package com.exchange.c2c.service.impl;

import com.exchange.c2c.mapper.CurrencyMapper;
import com.exchange.c2c.model.CurrencyDTO;
import com.exchange.c2c.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    @Autowired
    private CurrencyMapper currencyMapper;

    @Override
    public List<CurrencyDTO> findAll(String type) {
        return currencyMapper.findAllByType(type);
    }
}
