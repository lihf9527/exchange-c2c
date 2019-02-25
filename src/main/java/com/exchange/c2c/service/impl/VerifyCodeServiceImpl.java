package com.exchange.c2c.service.impl;

import com.exchange.c2c.common.util.Assert;
import com.exchange.c2c.common.util.RandomUtils;
import com.exchange.c2c.service.EmailService;
import com.exchange.c2c.service.SmsService;
import com.exchange.c2c.service.VerifyCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class VerifyCodeServiceImpl implements VerifyCodeService {
    private static final String template = "【ALiCoin】您的验证码：#code#，为了保护您的账户安全，请不要把验证码透露给别人";

    @Autowired
    private ValueOperations<String, String> valueOperations;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private SmsService smsService;
    @Autowired
    private EmailService emailService;

    @Override
    public void sendPhone(String phone) {
        String code = RandomUtils.randomString(6);
        boolean result = smsService.send(phone, template.replace("#code#", code));
        Assert.isTrue(result, "验证码发送失败,请稍后再试");
        saveCode(phone, code);
    }

    @Override
    public void sendEmail(String email) {
        String code = RandomUtils.randomString(6);
        boolean result = emailService.send(email, "ALiCoin", template.replace("#code#", code));
        Assert.isTrue(result, "验证码发送失败,请稍后再试");
        saveCode(email, code);
    }

    @Override
    public Long getSendTimes(String phoneOrEmail) {
        String times = valueOperations.get(getTimesKey(phoneOrEmail));
        return times == null ? null : Long.valueOf(times);
    }

    @Override
    public String getCodeKey(String phoneOrEmail) {
        return "verify_code.".concat(phoneOrEmail);
    }

    @Override
    public String getTimesKey(String phoneOrEmail) {
        return "verify_code_times.".concat(phoneOrEmail);
    }

    private void saveCode(String phoneOrEmail, String code) {
        valueOperations.set(getCodeKey(phoneOrEmail), code, 1, TimeUnit.MINUTES);
        Long num = valueOperations.increment(getTimesKey(phoneOrEmail));
        Objects.requireNonNull(num);
        if (num == 1) {
            redisTemplate.expire(getTimesKey(phoneOrEmail), 1, TimeUnit.HOURS);
        }
    }
}
