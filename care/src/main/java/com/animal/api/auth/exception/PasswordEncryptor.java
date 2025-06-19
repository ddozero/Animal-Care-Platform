package com.animal.api.auth.exception;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncryptor {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encrypted = encoder.encode("1234");  // ← 원하는 비밀번호 입력
        System.out.println("암호화된 비밀번호: " + encrypted);
    }
}
