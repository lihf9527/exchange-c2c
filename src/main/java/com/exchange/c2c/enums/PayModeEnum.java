package com.exchange.c2c.enums;

import com.exchange.c2c.common.EnumMsg;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayModeEnum implements EnumMsg<String> {
    ALIPAY_PAYMENT("1", "支付宝"),
    WE_CHAT_PAYMENT("2", "微信支付"),
    BANK_CARD_PAYMENT("3", "银行卡");

    private String value;
    private String name;
}
