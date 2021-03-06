package com.exchange.c2c.enums;

import com.exchange.c2c.common.EnumMsg;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AdvertTypeEnum implements EnumMsg<Integer> {
    BUY(1, "购买"),
    SELL(2, "出售");

    private Integer value;
    private String name;
}
