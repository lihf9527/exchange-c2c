package com.exchange.c2c.enums;

import com.exchange.c2c.common.EnumMsg;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VerifyModeEnum implements EnumMsg<Integer> {
    PHONE(1, "手机验证"),
    EMAIL(2, "邮箱验证"),
    GOOGLE(3, "谷歌验证");

    private Integer value;
    private String name;
}
