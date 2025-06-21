package com.animal.api.email.service;

public interface EmailCertificationService {

    void saveCertification(String email, String token, int minutesValid);
    boolean verifyCode(String email, String code);
}
