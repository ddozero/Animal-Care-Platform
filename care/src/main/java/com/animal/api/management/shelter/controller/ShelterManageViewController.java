package com.animal.api.management.shelter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shelter/manage")
public class ShelterManageViewController {
	
	@GetMapping
	public String volunteerList() { //보호시설 정보
		return "management/shelter/shelterInfo";
	}

}
