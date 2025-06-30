package com.animal.api.signup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignupViewController {

	//회원가입 유형선택 페이지
	@GetMapping("/signup")
	public String signup() {
		return "user/signup/signupTypeSelect";
	}
	
	//회원가입 -> 일반 사용자 회원가입 
	@GetMapping("/typeUser")
	public String signTypeUser() {
		return "user/signup/users/userAgreement";
	}
	//회원가입 -> 보호시설 사용자 회원가입
	@GetMapping("/typeShelter")
	public String signTypeSehlter() {
		return "user/signup/shelters/shelterAgreement";
	}
	
	//회원가입 -> 일반 사용자 정보입력 폼
	@GetMapping("/userForm")
	public String userSignupForm() {
		return "user/signup/users/userFrom";
	}
	
	//회원가입 -> 보호시설 사용자 정보입력 폼
	@GetMapping("/shelterForm")
	public String shelterSignupForm() {
		return "user/signup/shelters/shelterForm";
	}
	
	@GetMapping("/test")
	public String test() {
		return "user/signup/users/test";
	}
	@GetMapping("/test2")
	public String test2() {
		return "user/signup/users/test2";
	}
}
