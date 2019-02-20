package com.exchange.c2c.enums;

import com.exchange.c2c.common.EnumMsg;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CurrencyTypeEnum implements EnumMsg<String> {
    COIN("C", "数字货币"),
    FIAT("F", "法定货币");

    private String value;
    private String name;
}
