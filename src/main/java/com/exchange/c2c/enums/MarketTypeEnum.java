package com.exchange.c2c.enums;

import com.exchange.c2c.common.EnumMsg;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MarketTypeEnum implements EnumMsg<String> {
    SELL("1", "出售"),
    BUY("2", "购买");

    private String value;
    private String name;
}
