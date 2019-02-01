package com.exchange.c2c.enums;

import com.exchange.c2c.common.EnumMsg;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AppealResultEnum implements EnumMsg<Integer> {
    PROCESSING(1, "处理中"),
    SUCCESSFUL(2, "胜诉"),
    FAILURE(3, "败诉");

    private Integer value;
    private String name;
}
