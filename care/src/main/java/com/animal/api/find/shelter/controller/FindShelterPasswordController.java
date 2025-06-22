package com.animal.api.find.shelter.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.find.shelter.model.request.FindShelterPasswordInitRequestDTO;
import com.animal.api.find.shelter.model.request.FindShelterPasswordResetRequestDTO;
import com.animal.api.find.shelter.model.request.FindShelterPasswordVerifyRequestDTO;
import com.animal.api.find.shelter.service.FindShelterService;

import lombok.RequiredArgsConstructor;

/**
 * 보호시설 사용자 비밀번호 찾기 컨트롤러
 * @author Whistler95
 * @since 2025-06-22
 */
@RestController
@RequestMapping("/api/find/shelter/password")
@RequiredArgsConstructor
public class FindShelterPasswordController {

    private final FindShelterService findShelterService;

    /**
     * 1단계: 보호소 아이디 존재 여부 확인
     */
    @PostMapping("/init")
    public ResponseEntity<?> initShelterPasswordReset(@RequestBody @Valid FindShelterPasswordInitRequestDTO dto) {
        findShelterService.initShelterPasswordReset(dto.getUserid());
        return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<>(200, "존재하는 보호소 아이디입니다.", null));
    }

    /**
     * 2단계: 보호소 인증 코드 확인
     */
    @PostMapping("/verify")
    public ResponseEntity<?> verifyShelterPasswordResetCode(@RequestBody @Valid FindShelterPasswordVerifyRequestDTO dto) {
        findShelterService.verifyShelterPasswordResetCode(dto);
        return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<>(200, "인증 성공", null));
    }

    /**
     * 3단계: 보호소 비밀번호 재설정
     */
    @PostMapping("/reset")
    public ResponseEntity<?> resetShelterPassword(@RequestBody @Valid FindShelterPasswordResetRequestDTO dto) {
        findShelterService.resetShelterPassword(dto);
        return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<>(200, "비밀번호 재설정 완료", null));
    }
}
