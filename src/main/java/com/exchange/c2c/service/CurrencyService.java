package com.exchange.c2c.service;

import com.exchange.c2c.model.CurrencyDTO;

import java.util.List;

public interface CurrencyService {
    List<CurrencyDTO> findAll(String type);
}
