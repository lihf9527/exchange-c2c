package com.exchange.c2c.enums;

import com.exchange.c2c.common.EnumMsg;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayModeStatusEnum implements EnumMsg<String> {
    DISABLE("0", "禁用"),
    ENABLE("1", "启用");

    private String value;
    private String name;
}
