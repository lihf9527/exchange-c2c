package com.exchange.c2c.enums;

import com.exchange.c2c.common.EnumMsg;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatusEnum implements EnumMsg<Integer> {
    WAIT_PAY(1, "待支付"),
    WAIT_CONFIRM(2, "待确认"),
    CANCELED(3, "已取消"),
    APPEAL(4, "申诉中"),
    FINISHED(5, "完成");

    private Integer value;
    private String name;
}
