package com.exchange.c2c.mapper;

import com.exchange.c2c.model.CurrencyDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CurrencyMapper {
    Integer findCurrencyIdByCode(String code);

    List<CurrencyDTO> findAllByType(String type);

    boolean existsByTypeAndCode(@Param("type") String type, @Param("code") String code);
}
