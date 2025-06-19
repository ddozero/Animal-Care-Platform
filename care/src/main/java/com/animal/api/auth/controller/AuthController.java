package com.animal.api.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.auth.model.request.LoginRequestDTO;
import com.animal.api.auth.model.response.LoginResponseDTO;
import com.animal.api.auth.service.AuthService;
import com.animal.api.common.model.OkResponseDTO;

import lombok.RequiredArgsConstructor;

/**
 * 사용자 기준 로그인 기능 컨트롤러 클래스
 * 
 * @author Whistler95
 * @since 2025-06-16
 * @see com.animal.api.model.response.LoginResponseDTO
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDTO dto) {
		LoginResponseDTO response = authService.login(dto);
		return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<LoginResponseDTO>(200, "로그인 성공", response));
	}

}
