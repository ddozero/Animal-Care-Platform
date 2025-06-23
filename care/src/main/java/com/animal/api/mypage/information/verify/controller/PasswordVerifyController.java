package com.animal.api.mypage.information.verify.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.auth.model.response.LoginResponseDTO;
import com.animal.api.common.aop.util.SessionUtils;
import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.mypage.information.verify.model.request.PasswordVerifyRequestDTO;
import com.animal.api.mypage.information.verify.service.PasswordVerifyService;

import lombok.RequiredArgsConstructor;

/**
 * 내 정보 수정 인증 컨트롤러
 * 
 * @author Whistler95
 * @since 2025-06-23
 */

@RestController
@RequestMapping("/api/mypage/information")
@RequiredArgsConstructor
public class PasswordVerifyController {

	private final PasswordVerifyService passwordVerifyService;

	/**
	 * 내 정보 수정 진입 시 내 비밀번호 확인 검증
	 * 
	 * @param requestDTO
	 * @param request
	 * @return 비밀번호 일치 여부
	 */
	@PostMapping("/verify-password")
	public ResponseEntity<?> verifyPassword(@Valid @RequestBody PasswordVerifyRequestDTO requestDTO,
			HttpServletRequest request) {

		LoginResponseDTO loginUser = SessionUtils.getLoginUser(request);
		if (loginUser == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new OkResponseDTO<>(401, "로그인 정보가 없습니다", null));
		}

		boolean matched = passwordVerifyService.verifyPassword(loginUser.getIdx(), requestDTO.getPassword());
		if (!matched) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new OkResponseDTO<>(401, "비밀번호가 일치하지 않습니다", null));
		}

		return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<>(200, "비밀번호가 확인되었습니다", null));
	}

}
