package com.animal.api.signup.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.animal.api.common.aop.email.RequireEmailVerified;
import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.signup.model.request.ShelterSignupRequestDTO;
import com.animal.api.signup.model.request.UserSignupRequestDTO;
import com.animal.api.signup.service.ShelterSignupService;
import com.animal.api.signup.service.UserSignupService;

/**
 * 일반 사용자/ 보호시설 회원가입 컨트롤러 클래스
 * @author Whistler95
 * @since 2025-06-19
 * @see com.animal.api.signup.model.request.UserSignupRequestDTO, com.animal.api.signup.model.request.ShelterSignupRequestDTO
 */
@RestController
@RequestMapping("/api/signup")
public class SignupController {

	@Autowired
	private ShelterSignupService signupService;
	@Autowired
	private UserSignupService userSignupService;
	
	/**
	 * 일반 사용자 회원가입 메서드
	 * @param UserSignupRequestDTO 회원가입 폼
	 * @Valid UserSignupRequestDTO 유효성 검사 
	 * @RequireEmailVerified 메서드 실행 전 AOP 실행 
	 * @return 회원가입 완료
	 */
	//일반 사용자 회원가입
	@PostMapping("/user")
	@RequireEmailVerified
	public ResponseEntity<OkResponseDTO<String>> signup(@Valid @RequestBody UserSignupRequestDTO dto, HttpServletRequest request){
		
		userSignupService.signupUser(dto);
		
		//인증 완료 후 세션 정리
		HttpSession session = request.getSession(false);
		if(session != null) {
			
			session.removeAttribute("emailVerified");
			session.removeAttribute("emailAuthCode");
			session.removeAttribute("emailAuthTarget");
			
		}
	    
		return ResponseEntity.status(HttpStatus.CREATED).body(new OkResponseDTO<>(201, "회원가입이 완료되었습니다", null));
	}
	/**
	 * 보호시설 사용자 회원가입 메서드
	 * 
	 * @param ShelterSignupRequestDTO 회원가입 폼
	 * @Valid ShelterSignupRequestDTO 유효성 검사
	 * @MultipartFile 민간보호소의 경우 사업자등록증 첨부파일 업로드 
	 * @RequireEmailVerified 메서드 실행 전 AOP 실행 
	 * @return 회원가입 요청 완료 
	 */
    // 보호소 회원가입
    @PostMapping("/shelter")
    @RequireEmailVerified
    public ResponseEntity<OkResponseDTO<String>> signupShelter(@Valid @ModelAttribute ShelterSignupRequestDTO dto, 
    		HttpServletRequest request, @RequestPart(value = "businessFile", required = false) MultipartFile businessFile) {
    	
        signupService.signupShelter(dto, businessFile);
        
		//인증 완료 후 세션 정리
		HttpSession session = request.getSession(false);
		if(session != null) {
			
			session.removeAttribute("emailVerified");
			session.removeAttribute("emailAuthCode");
			session.removeAttribute("emailAuthTarget");
			
		}
		
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new OkResponseDTO<>(201, "보호소 회원가입이 요청이 완료되었습니다.", null));
    }
	
}
