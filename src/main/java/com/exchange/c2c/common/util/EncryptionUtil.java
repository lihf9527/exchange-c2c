package com.exchange.c2c.common.util;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;

public class EncryptionUtil {
    private static final String password = "evJLdIo23L";
    private static final String algorithm = "PBEWITHMD5ANDDES";
    private static final PooledPBEStringEncryptor encryptor;

    static {
        encryptor = new PooledPBEStringEncryptor();
        encryptor.setPoolSize(4);
        encryptor.setPassword(password);
        encryptor.setAlgorithm(algorithm);
    }

    public static String encrypt(String input) {
        return encryptor.encrypt(input);
    }

    public static String decrypt(String encryptedMessage) {
        try {
            return encryptor.decrypt(encryptedMessage);
        } catch (Exception e) {
            return encryptedMessage;
        }
    }
}
