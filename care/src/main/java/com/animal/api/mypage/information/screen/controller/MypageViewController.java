package com.animal.api.mypage.information.screen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MypageViewController {

	@GetMapping("/mypage")
	public String mypage () {
		return "user/mypage/mypageScreen";
	}
}
