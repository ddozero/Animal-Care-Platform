package com.animal.api.signup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.signup.model.request.UserSignupRequestDTO;
import com.animal.api.signup.service.SignupService;

@RestController
@RequestMapping("/signup")
public class SignupController {

	@Autowired
	private SignupService signupService;
	
	//일반 사용자 회원가입
	@PostMapping("/user")
	public ResponseEntity<OkResponseDTO<String>> signup(@RequestBody UserSignupRequestDTO dto){
		signupService.signupUser(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(new OkResponseDTO<>(201, "회원가입이 완료되었습니다", null));
	}
}
