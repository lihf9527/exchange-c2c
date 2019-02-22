package com.exchange.c2c.service;

public interface EmailService {
    /**
     * @param to      收件人地址 多个联系人用半角“,”隔开
     * @param subject 邮件标题（200个字符以内）
     * @param text    纯文本邮件正文（5000个字符以内）
     */
    boolean send(String to, String subject, String text);
}
