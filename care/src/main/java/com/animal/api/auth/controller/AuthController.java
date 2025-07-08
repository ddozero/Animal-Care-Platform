package com.animal.api.auth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
 * @see com.animal.api.auth.model.response.LoginResponseDTO
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	/**
	 * 일반 , 보호시설 사용자 통합 로그인 메서드
	 * 
	 * @param LoginRequestDTO 로그인 폼
	 * @session 로그인 한 사용자만의 정보 저장
	 * @return 로그인 한 사용자의 정보 
	 */
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDTO dto, HttpServletRequest request) {

		LoginResponseDTO user = authService.login(dto);
		
		//기존 세션 무효화
		HttpSession oldSession = request.getSession(false);
		if(oldSession != null) {
			oldSession.invalidate();
		}
		
		//새로운 세션 생성
		HttpSession session = request.getSession(true);
		session.setAttribute("loginUser", user);
		
		return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<LoginResponseDTO>(200, "로그인 성공", user));
	}


	
}
