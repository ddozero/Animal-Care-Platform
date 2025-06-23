package com.animal.api.email.service;

public interface EmailCertificationService {

    void saveCertification(String email, String token, int minutesValid);
    boolean verifyCode(String email, String code);
    
    //이메일 변졍 전용 인증번호 저장
    void saveCertificationWithUser(int userIdx, String email, String code, int expireMinutes);
}
