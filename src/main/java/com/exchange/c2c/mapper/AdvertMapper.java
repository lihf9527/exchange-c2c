package com.exchange.c2c.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exchange.c2c.entity.Advert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AdvertMapper extends BaseMapper<Advert> {
}
