package com.exchange.c2c.enums;

import com.exchange.c2c.common.EnumMsg;
import lombok.Getter;

@Getter
public enum HistoryOrderStatusEnum implements EnumMsg<Integer> {
    CANCELED(OrderStatusEnum.CANCELED),
    FINISHED(OrderStatusEnum.FINISHED);

    private Integer value;
    private String name;

    HistoryOrderStatusEnum(OrderStatusEnum orderStatus) {
        this.value = orderStatus.getValue();
        this.name = orderStatus.getName();
    }
}
