package com.animal.api.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthViewController {

	//메인 페이지
	@GetMapping("/index")
	public String indexPage() {
		return "common/index/index";
	}
		
	//로그인 페이지
	@GetMapping("/login")
	public String loginPage() {
		return "user/auth/login/login";
	}
	
}
