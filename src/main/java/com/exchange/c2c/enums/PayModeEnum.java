package com.exchange.c2c.enums;

import com.exchange.c2c.common.EnumMsg;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayModeEnum implements EnumMsg<String> {
    ALIPAY_PAYMENT("1", "支付宝支付", AccountTypeEnum.Alipay),
    WE_CHAT_PAYMENT("2", "微信支付", AccountTypeEnum.Wechat),
    BANK_CARD_PAYMENT("3", "银行卡支付", AccountTypeEnum.BANK_CARD);

    private String value;
    private String name;
    private AccountTypeEnum accountType;
}
