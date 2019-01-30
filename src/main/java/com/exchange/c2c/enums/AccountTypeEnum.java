package com.exchange.c2c.enums;

import com.exchange.c2c.common.EnumMsg;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountTypeEnum implements EnumMsg<Integer> {
    Alipay(1, "支付宝"),
    Wechat(2, "微信"),
    BANK_CARD(3, "银行卡");

    private Integer value;
    private String name;
}
