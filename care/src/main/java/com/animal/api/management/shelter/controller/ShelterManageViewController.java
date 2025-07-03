package com.animal.api.management.shelter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/management/shelters")
public class ShelterManageViewController {
	
	@GetMapping()
	public String ShelterInfo() { //보호시설 정보
		return "management/shelter/shelterInfo";
	}
	
	@GetMapping("/boards")
	public String shelterNoticeList() { //보호시설 공지사항 목록
		return "management/shelter/shelterNoticeList";
	}
	
	@GetMapping("/boards/{idx}")
	public String shelterNoticeDetail(@PathVariable int idx, Model model) { //공지사항 컨텐츠
		model.addAttribute("idx",idx);
		return "management/shelter/shelterNoticeContent";
	}
	
	@GetMapping("/boards/write")
	public String shelterNoticeWrite() { //공지사항 등록폼
		return "management/shelter/shelterNoticeWrite";
	}
	
	@GetMapping("/boards/update/{idx}")
	public String shelterNoticeUpdate(@PathVariable int idx, Model model) {
	    model.addAttribute("idx", idx);
	    return "management/shelter/shelterNoticeUpdate";  // 수정 폼으로 이동
	}
	
	@GetMapping("/adoptionReview/reply")
	  public String showReviewPage() {
	   return "management/shelter/shelterReplyForm"; // 해당 JSP 페이지로 이동

	}


}
