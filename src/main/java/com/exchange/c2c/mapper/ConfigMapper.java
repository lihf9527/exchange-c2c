package com.exchange.c2c.mapper;

import com.exchange.c2c.entity.Config;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ConfigMapper {
    List<Config> findAll(String code);

    boolean exists(@Param("code") String code, @Param("value") String value);
}
