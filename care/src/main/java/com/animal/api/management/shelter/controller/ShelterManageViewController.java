package com.animal.api.management.shelter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shelter")
public class ShelterManageViewController {
	
	@GetMapping("/manage")
	public String ShelterInfo() { //보호시설 정보
		return "management/shelter/shelterInfo";
	}
	
	@GetMapping("/board")
	public String shelterNoticeList() { //보호시설 공지사항 목록
		return "management/shelter/shelterNoticeList";
	}
	
	@GetMapping("/board/{idx}")
	public String shelterNoticeDetail(@PathVariable int idx, Model model) { //공지사항 컨텐츠
		model.addAttribute("idx",idx);
		return "user/support/noticeContent";
	}


}
