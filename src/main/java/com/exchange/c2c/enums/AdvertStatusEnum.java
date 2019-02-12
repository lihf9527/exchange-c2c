package com.exchange.c2c.enums;

import com.exchange.c2c.common.EnumMsg;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AdvertStatusEnum implements EnumMsg<Integer> {
    ENABLE(1, "上架"),
    DISABLE(0, "下架");

    private Integer value;
    private String name;
}
