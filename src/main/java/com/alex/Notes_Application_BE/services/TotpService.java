package com.alex.Notes_Application_BE.services;

import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

public interface TotpService {

    GoogleAuthenticatorKey generateSecret();

    String getQrCodeUrl(GoogleAuthenticatorKey secret, String username);

    boolean verifyCode(String secret, int code);
}
