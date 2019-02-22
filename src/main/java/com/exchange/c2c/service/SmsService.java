package com.exchange.c2c.service;

public interface SmsService {
    /**
     * @param mobile 手机号码
     * @param msg    短信内容
     */
    boolean send(String mobile, String msg);
}
