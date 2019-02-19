package com.exchange.c2c.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CurrencyMapper {
    Integer findCurrencyIdByCode(String code);
}
