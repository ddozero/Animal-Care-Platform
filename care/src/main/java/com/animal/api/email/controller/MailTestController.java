package com.animal.api.email.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.email.service.EmailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class MailTestController {

	private final EmailService emailService;
	
	@GetMapping("/send-mail")
	public ResponseEntity<?> sendTestMail(@RequestParam String to){
		emailService.sendEmail(to, "테스트메일", "이건 테스트메일입니다아");
		return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<>(200, "테스트 메일 발송 성공", Map.of("to",to)));
	}
}
