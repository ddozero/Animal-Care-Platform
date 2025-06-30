package com.animal.api.support.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/support")
public class UserSupportViewController {
	
	@GetMapping
	public String supportList() { //공지사항 목록
		return "user/support/noticeList";
	}
}
