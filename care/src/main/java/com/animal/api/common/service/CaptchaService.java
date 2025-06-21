package com.animal.api.common.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * 캡챠 추가 전 예시 서비스
 * @author Whistler95
 *
 */
@Service
@Primary
public class CaptchaService {

    public boolean verify(String captcha) {
        // 예시: 고정된 캡차값이 "abc123"일 경우 통과
        return "abc123".equalsIgnoreCase(captcha);
    }
}
