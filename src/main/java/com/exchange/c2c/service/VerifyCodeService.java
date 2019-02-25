package com.exchange.c2c.service;

public interface VerifyCodeService {
    void sendPhone(String phone);

    void sendEmail(String email);

    Long getSendTimes(String phoneOrEmail);

    String getCodeKey(String phoneOrEmail);

    String getTimesKey(String phoneOrEmail);
}
