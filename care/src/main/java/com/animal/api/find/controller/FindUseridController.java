package com.animal.api.find.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.find.model.request.FindUserIdRequestDTO;
import com.animal.api.find.model.response.FindUserIdResponseDTO;
import com.animal.api.find.service.FindUserService;

import lombok.RequiredArgsConstructor;
/**
 * 일반 사용자 아이디 찾기 컨트롤러
 * @author Whistler95
 * @since 2025-06-21
 * @see FindUserIdRequestDTO 아이디 찾기 입력 값
 *
 */
@RestController
@RequestMapping("/api/find/user")
@RequiredArgsConstructor
public class FindUseridController {

	private final FindUserService findService;
	
	@PostMapping("/id")
	public ResponseEntity<?> findUserId(@RequestBody FindUserIdRequestDTO request) {
	    FindUserIdResponseDTO response = findService.findUserId(request.getName(), request.getEmail());

	    return ResponseEntity.ok(new OkResponseDTO<>(200, "아이디 찾기 성공", response));
	}
}
