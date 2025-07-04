package com.animal.api.management.shelter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	//봉사 리뷰 답글
	@GetMapping("/volunteerReview/reply")
	public String shelterReplyVR(@RequestParam int reviewIdx,
	                             @RequestParam int volunteerIdx,
	                             Model model) {
	    model.addAttribute("reviewIdx", reviewIdx);
	    model.addAttribute("volunteerIdx", volunteerIdx);
	    return "management/shelter/shelterReplyFormVR";
	}
	
	//봉사 리뷰 수정
	@GetMapping("/volunteerReview/reply/update")
	public String showReviewPageVR(@RequestParam int reviewIdx,
		                             Model model) {
		model.addAttribute("reviewIdx", reviewIdx);
		return "management/shelter/shelterReplyUpdateVR";
	}
	
	//입양 리뷰 답글
	@GetMapping("/adoptionReview/reply")
	public String showReplyAR(@RequestParam int reviewIdx,
	                             @RequestParam int animalIdx,
	                             Model model) {
	    model.addAttribute("reviewIdx", reviewIdx);
	    model.addAttribute("volunteerIdx", animalIdx);
	    return "management/shelter/shelterReplyFormAR";
	}


}
