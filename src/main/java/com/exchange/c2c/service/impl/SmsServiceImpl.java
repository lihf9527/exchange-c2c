package com.exchange.c2c.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.exchange.c2c.service.SmsService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Slf4j
@Setter
@Component
@ConfigurationProperties(prefix = "sms")
public class SmsServiceImpl implements SmsService {
    private String account;
    private String password;
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public boolean send(String mobile, String msg) {
        String body = new JSONObject() {{
            put("account", account);
            put("password", password);
            put("mobile", mobile);
            put("msg", msg);
        }}.toJSONString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        JSONObject result = restTemplate.postForObject(url, new HttpEntity<>(body, headers), JSONObject.class);
        Objects.requireNonNull(result);
        log.info("发送短信 ==> mobile={}, msg={}, result={}", mobile, msg, result.toJSONString());
        // {"code": "114", "error":"客户端IP错误", "msgid":""}
        return !Objects.equals("114", result.getString("code"));
    }
}
