package com.animal.api.signup.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.signup.model.request.ShelterSignupRequestDTO;
import com.animal.api.signup.model.request.UserSignupRequestDTO;
import com.animal.api.signup.service.SignupService;

/**
 * 일반 사용자/ 보호시설 회원가입 컨트롤러 클래스
 * @author Whistler95
 * @since 2025-06-19
 * @see com.animal.api.signup.model.request.UserSignupRequestDTO, com.animal.api.signup.model.request.ShelterSignupRequestDTO
 */
@RestController
@RequestMapping("/signup")
public class SignupController {

	@Autowired
	private SignupService signupService;
	
	/**
	 * 일반 사용자 회원가입 메서드
	 * @param UserSignupRequestDTO 회원가입 폼
	 * @Valid UserSignupRequestDTO 유효성 검사 
	 * @return 회원가입 완료
	 */
	//일반 사용자 회원가입
	@PostMapping("/user")
	public ResponseEntity<OkResponseDTO<String>> signup(@Valid @RequestBody UserSignupRequestDTO dto){
		signupService.signupUser(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(new OkResponseDTO<>(201, "회원가입이 완료되었습니다", null));
	}
	/**
	 * 보호시설 사용자 회원가입 메서드
	 * @param ShelterSignupRequestDTO 회원가입 폼
	 * @return 회원가입 요청 완료 
	 */
    // 보호소 회원가입
    @PostMapping("/shelter")
    public ResponseEntity<OkResponseDTO<String>> signupShelter(@RequestBody ShelterSignupRequestDTO dto) {
        signupService.signupShelter(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new OkResponseDTO<>(201, "보호소 회원가입이 요청이 완료되었습니다.", null));
    }
	
}
