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
	public String volunteerDetail(@PathVariable int idx, Model model) {
	    model.addAttribute("idx",idx);
	    return "user/volunteers/volunteerDetail";
	}

}
