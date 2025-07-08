package com.animal.api.volunteers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/volunteers")
public class UserVolunteersViewController {
	
	@GetMapping
	public String volunteerList() { //봉사 목록
		return "user/volunteers/volunteersList";
	}
	
	@GetMapping("/{idx}")
	public String volunteerDetail(@PathVariable int idx, Model model) { //봉사 상세정보
	    model.addAttribute("idx",idx);
	    return "user/volunteers/volunteersContent";
	}
	
	@GetMapping("/{idx}/submit")
	public String volunteerSubmit(@PathVariable int idx) { //봉사 신청
		return "user/volunteers/volunteersSubmit";
	}
	
	@GetMapping("/calendar")
	public String volunteerCalender() { //봉사 캘린더
		return "user/volunteers/volunteersCal";
	}
	

	

}
