package com.exchange.c2c.enums;

import com.exchange.c2c.common.EnumMsg;
import lombok.Getter;

@Getter
public enum CurrentOrderStatusEnum implements EnumMsg<Integer> {
    WAIT_PAY(OrderStatusEnum.WAIT_PAY),
    WAIT_CONFIRM(OrderStatusEnum.WAIT_CONFIRM),
    APPEAL(OrderStatusEnum.APPEAL);

    private Integer value;
    private String name;

    CurrentOrderStatusEnum(OrderStatusEnum orderStatus) {
        this.value = orderStatus.getValue();
        this.name = orderStatus.getName();
    }
}
