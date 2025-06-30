package com.animal.api.support.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/support")
public class UserSupportViewController {
	
	@GetMapping
	public String supportList() { //공지사항 목록
		return "user/support/noticeList";
	}
	
	@GetMapping("/{idx}")
	public String supportContent(@PathVariable int idx, Model model) { //공지사항 컨텐츠
		model.addAttribute("idx",idx);
		return "user/support/noticeContent";
	}
}
