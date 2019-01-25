package com.exchange.c2c.common.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;

public class UserPwdUtil {

    public static String encrypt(String plainPassword, Long signupTime, String signupIp) {
        String enc = DigestUtils.md5Hex(plainPassword);
        if (signupTime != null)
            enc += "_" + signupTime;
        if (signupIp != null)
            enc += "_" + signupIp;
        return DigestUtils.md5Hex(enc);
    }

    /**
     * MD5加密方法
     */
    public static String MD5(String key) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str).toLowerCase();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * F8 1.0 密码生成规则
     */
    public static String twoTimesMD5Encrypt(String str) {
        return MD5(MD5(str));
    }
}
