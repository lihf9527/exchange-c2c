package com.exchange.c2c.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.exchange.c2c.service.EmailService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Slf4j
@Setter
@Component
@ConfigurationProperties(prefix = "email")
public class EmailServiceImpl implements EmailService {
    private String appid;
    private String appkey;
    private String from;
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public boolean send(String to, String subject, String text) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("appid", appid);
        params.add("to", to);
        params.add("subject", subject);
        params.add("text", text);
        params.add("from", from);
        params.add("signature", appkey);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        JSONObject result = restTemplate.postForObject(url, new HttpEntity<>(params, headers), JSONObject.class);
        Objects.requireNonNull(result);
        log.info("发送邮件 ==> to={}, subject={}, text={}, result={}", to, subject, text, result.toJSONString());
        // {"status":"success","return":[{"send_id":"838fd636c3fae771d3be91f11f0f3852","to":"lihf@sina.cn"}]}
        // {"status":"error","code":109,"msg":"Invalid appkey"}
        return Objects.equals("success", result.getString("status"));
    }
}
