package com.animal.api.email.controller;

import java.util.Map;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.email.model.request.CertificationRequestDTO;
import com.animal.api.email.model.request.EmailVerificationRequestDTO;
import com.animal.api.email.service.EmailCertificationService;
import com.animal.api.email.service.EmailService;

import lombok.RequiredArgsConstructor;

/**
 * @author Whistler95
 * @since 2024-06-21
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email/find")
public class EmailCertificationController {

	private final EmailService emailService;
	private final EmailCertificationService emailCertificationService;
	
    /**
     * 이미 가입된 사용자 이메일 대상으로 인증번호 발송 및 CERTIFICATIONS 테이블 정보 저장
     */
	@PostMapping("/send-code")
	public ResponseEntity<?> sendCertificationCode(@RequestBody CertificationRequestDTO request){
		
	    String email = request.getEmail();
	    
		//1.인증번호 생성
		String code = String.format("%06d",new Random().nextInt(999999));
		
        // 2. 이메일 발송
        String subject = "[Animal Care] 아이디/비빔번호 찾기 인증번호 안내";
        String body = "인증번호는 [" + code + "] 입니다. 5분 이내로 입력해주세요.";

        emailService.sendEmail(email, subject, body);

        // 3. 인증번호 저장 (DB)
        emailCertificationService.saveCertification(request.getEmail(), code, 5); // 5분 유효

        return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<>(200, "인증번호 전송 완료", Map.of("to", email)));
    }
	
	/**
	 * 인증번호 검증 요청 처리
	 * @param email 사용자 이메일
	 * @param code 사용자가 입력한 인증번호
	 * @return 인증 성공 여부
	 */
	@PostMapping("/check-code")
	public ResponseEntity<?> checkCertificationCode(@RequestBody EmailVerificationRequestDTO request) {
		
		//실패 시 알아서 CustomException throw
		emailCertificationService.verifyCode(request.getEmail(), request.getCode()); 

	    return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<>(200, "이메일 인증 완료", null));
	}
}
