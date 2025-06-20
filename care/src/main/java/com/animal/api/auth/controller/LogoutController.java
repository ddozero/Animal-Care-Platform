package com.animal.api.auth.controller;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.common.model.OkResponseDTO;

@RestController
@RequestMapping("/api/auth")
public class LogoutController {

	@PostMapping("/logout")
	public ResponseEntity<OkResponseDTO<String>> logout(HttpSession session){
		if (session != null) {
		    session.invalidate();
		}
		return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<>(200, "로그아웃이 완료되었습니다.", null));
	}
}
