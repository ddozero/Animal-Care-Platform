package com.animal.api.email.controller;

import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.email.service.EmailService;

import lombok.RequiredArgsConstructor;

/**
 * 
 * @author Whistler95
 * @since 2025-06-20
 * @return 이메일 인증 완료
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email")
public class EmailVerificationController {

	private final EmailService emailService;
	/**
	 * 
	 * @param email 사용자에게 받은 이메일 값
	 * @param session 인증번호 검증을 위한 인증번호 
	 * @return 이메일 성공 
	 */
	@PostMapping("/verify")
	public ResponseEntity<?> sendVerificationCode(@RequestParam String email, HttpSession session){
		//인증번호 6자리 숫자 랜덤 생성
		String code = String.format("%06d", new Random().nextInt(999999));
		
		//이메일 발송 내역
		String subject = "[유기동물 통합 플랫폼] 이메일 인증번호 안내";
		String body = "인증번호는 [" + code + "] 입니다. 회원가입 화면에 정확히 입력 바랍니다.";
		
		emailService.sendEmail(email, subject, body);
		
		
		//세션에 인증코드 저장
		session.setAttribute("emailAuthCode", code);
		session.setAttribute("emailAuthTarget", email);
		//이전 인증 내역 제거
		session.removeAttribute("emailVerified");
		
		return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<>(200, "인증번호 전송 완료", Map.of("to",email)));
	}
	
	/**
	 * 
	 * @param email 인증번호 받으려는 이메일
	 * @param code 인증번호에 입력됬었던 인증숫자
	 * @param session 에 감겨있는 email 과 code
	 * @return 인증 성공 
	 */
	@PostMapping("/verify/code")
	public ResponseEntity<?> verifyCode(@RequestParam String email, @RequestParam String code, HttpSession session){
		
		String savedCode = (String) session.getAttribute("emailAuthCode");
		String savedEmail = (String) session.getAttribute("emailAuthTarget"); 
		
		//이메일 인증 안했을 시
		if(savedCode == null || !email.equals(savedEmail)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new OkResponseDTO<>(400, "먼저 이메일 인증 요청을 해주세요", null));
		}
		
		//인증번호 일치 하지 않을 시
		if(!savedCode.equals(code)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new OkResponseDTO<>(401, "인증번호가 일치하지 않습니다", null));
		}
		
		//인증완료 값 저장
		session.setAttribute("emailVerified", true);
		return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<>(200, "이메일 인증 완료", null));
	}
}
