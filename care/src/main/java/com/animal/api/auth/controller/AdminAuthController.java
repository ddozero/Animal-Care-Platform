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
 * 
 * @author Whistler95
 * @since 2025-06-20
 * @see com.animal.api.auth.model.response.LoginResponseDTO
 *
 */
@RestController
@RequestMapping("/api/auth/admin")
@RequiredArgsConstructor
public class AdminAuthController {

	private final AuthService authService;
	
	/**
	 * 
	 * @param LoginRequestDTO 관리자 계정
	 * @session 관리자 간단 정보 저장
	 * @return 로그인 한 관리자의 정보
	 */
	
	@PostMapping("/login")
	public ResponseEntity<?> loginAdmin(@RequestBody LoginRequestDTO dto, HttpServletRequest request){
		
		LoginResponseDTO admin = authService.loginAdmin(dto);
		
		HttpSession session = request.getSession(true);
		session.setAttribute("loginAdmin", admin);
		
		return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<LoginResponseDTO>(200, "관리자 로그인 성공", admin));
	}
}
