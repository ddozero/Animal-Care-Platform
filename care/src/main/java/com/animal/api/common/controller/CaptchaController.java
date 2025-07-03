package com.animal.api.common.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.impl.DefaultKaptcha;

@RestController
@RequestMapping("/api/captcha")
public class CaptchaController {

    private final DefaultKaptcha captchaProducer;

    public CaptchaController(DefaultKaptcha captchaProducer) {
        this.captchaProducer = captchaProducer;
    }

    @GetMapping("/image")
    public void getCaptchaImage(HttpServletResponse response, HttpSession session) throws IOException {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.setContentType("image/jpeg");

        String captchaText = captchaProducer.createText();  // 랜덤 문자열 생성
        session.setAttribute("CAPTCHA", captchaText);       // 세션에 저장
        
        System.out.println("🔑 요청 세션 ID: " + session.getId());
        System.out.println("이미지 생성 시 CAPTCHA: " + captchaText);

        BufferedImage captchaImage = captchaProducer.createImage(captchaText);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(captchaImage, "jpg", out);
        out.flush();
        out.close();
    }
    
    @GetMapping("/ping")
    public ResponseEntity<?> ping(HttpSession session) {
        // 세션 강제 생성용
        return ResponseEntity.ok().build();
    }
}
