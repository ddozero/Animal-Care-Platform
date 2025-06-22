package com.animal.api.find.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.find.model.request.FindUserPasswordInitRequestDTO;
import com.animal.api.find.model.request.FindUserPasswordResetRequestDTO;
import com.animal.api.find.model.request.FindUserPasswordVerifyRequestDTO;
import com.animal.api.find.service.FindUserService;

import lombok.RequiredArgsConstructor;

/**
 * 일반 사용자 비밀번호 찾기 컨트롤러
 * 
 * @author 
 * @since 2025-06-21
 */
@RestController
@RequestMapping("/api/find/password")
@RequiredArgsConstructor
public class FindUserPasswordController {
	
    private final FindUserService findPasswordService;

    
    @PostMapping("/init")
    public ResponseEntity<?> initUserPasswordReset(@RequestBody FindUserPasswordInitRequestDTO dto) {
        findPasswordService.initUserPasswordReset(dto.getUserid());
        return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<>(200, "존재하는 사용자입니다.", null));
    }
    
    /**
     * 이름 + 이메일 + 인증번호로 사용자 확인 (비밀번호 재설정 전 단계)
     */
    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestBody FindUserPasswordVerifyRequestDTO dto) {
        findPasswordService.verifyUserPasswordResetCode(dto);
        return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<>(200, "사용자 인증 성공", null));
    }

    /**
     * 비밀번호 재설정 (아이디 기준)
     */
    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid FindUserPasswordResetRequestDTO dto) {
        findPasswordService.resetUserPassword(dto);
        return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<>(200, "비밀번호가 성공적으로 변경되었습니다.", null));
    }
}
