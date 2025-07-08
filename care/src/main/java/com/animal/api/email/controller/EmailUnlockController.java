package com.animal.api.email.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.common.aop.email.RequireEmailVerified;
import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.email.model.request.UnlockRequestDTO;
import com.animal.api.email.model.request.UnlockVerifyDTO;
import com.animal.api.email.service.EmailUnlockService;

/**
 * 계정 잠금 해제용 이메일 인증 컨트롤러
 * 
 * 	로그인 실패 5회 이상으로 계정이 잠긴 경우
 * 	이메일 인증을 통해 계정을 수동으로 해제하는 기능 제공
 * 	기존 이메일 인증 AOP (@RequireEmailVerified) 재사용
 * 
 * @author whister95
 * @since 2025-06-27
 */
@RestController
@RequestMapping("/api/email")
public class EmailUnlockController {
	
    @Autowired
    private EmailUnlockService emailUnlockService;

    /**
     * 계정 잠금 해제 인증코드 요청 API
     * 
     * 입력된 이메일로 인증코드 전송
     * 인증코드는 HttpSession에 저장됨
     * 
     * @param req { email }
     * @param session HttpSession
     * @return 성공 메시지
     */
    @PostMapping("/unlock-request")
    public ResponseEntity<?> sendUnlockCode(@RequestBody UnlockRequestDTO req, HttpSession session) {
    	
        emailUnlockService.sendUnlockCode(req.getEmail(), session);

        return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<>(200, "잠금 해제용 인증코드를 전송했습니다.", null));
    }
    /**
     * 계정 잠금 해제 인증코드 검증 API
     * 
     * @RequireEmailVerified AOP로 코드 검증 선처리
     * 인증 성공 시 해당 세션의 사용자 계정 잠금 해제 (LOCKED = 0, LOCK_COUNT = 0)
     * 
     * @param req { code }
     * @param session HttpSession
     * @return 성공 메시지
     */
    @PostMapping("/unlock-verify")
    @RequireEmailVerified
    public ResponseEntity<?> unlockAccount(@RequestBody UnlockVerifyDTO req, HttpSession session) {
    	
        emailUnlockService.verifyAndUnlock(req.getCode(), session);
      
        return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<>(200, "계정 잠금이 해제되었습니다.", null));
    }
}
