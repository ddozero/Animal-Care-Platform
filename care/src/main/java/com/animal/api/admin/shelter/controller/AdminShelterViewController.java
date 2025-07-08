package com.animal.api.admin.shelter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/shelters")
public class AdminShelterViewController {

	@GetMapping
	public String shelterJoinList() {
		return "/admin/shelter/shelterJoinList";
	}

}
