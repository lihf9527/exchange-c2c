package com.exchange.c2c.service;

public interface GoogleAuthService {
    void setWindowSize(int s);

    String generateSecretKey();

    String getQRBarcodeURL(String user, String host, String secret);

    String getQRBarcode(String user, String secret);

    boolean checkCode(String secret, long code, long timeMsec);
}
