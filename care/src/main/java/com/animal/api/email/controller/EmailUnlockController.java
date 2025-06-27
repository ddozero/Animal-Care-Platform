package com.animal.api.email.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.auth.exception.CustomException;
import com.animal.api.common.aop.email.RequireEmailVerified;
import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.email.model.request.UnlockRequestDTO;
import com.animal.api.email.model.request.UnlockVerifyDTO;
import com.animal.api.email.service.EmailUnlockService;

@RestController
@RequestMapping("/api/email")
public class EmailUnlockController {
	
    @Autowired
    private EmailUnlockService emailUnlockService;

    // 잠금 해제용 인증 요청
    @PostMapping("/unlock-request")
    public ResponseEntity<?> sendUnlockCode(@RequestBody UnlockRequestDTO req, HttpSession session) {
    	
        emailUnlockService.sendUnlockCode(req.getEmail(), session);

        return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<>(200, "잠금 해제용 인증코드를 전송했습니다.", null));
    }

    //  인증코드 확인용 (기존 인증코드 확인 흐름 재사용)
    @PostMapping("/unlock-verify")
    @RequireEmailVerified
    public ResponseEntity<?> unlockAccount(@RequestBody UnlockVerifyDTO req, HttpSession session) {
    	
        emailUnlockService.verifyAndUnlock(req.getCode(), session);
      
        return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<>(200, "계정 잠금이 해제되었습니다.", null));
    }
}
