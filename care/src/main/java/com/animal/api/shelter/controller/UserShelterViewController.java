package com.animal.api.shelter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shelters")
public class UserShelterViewController {

	@GetMapping
	public String shelterList() {
		return "user/shelter/shelterList";
	}

	@GetMapping("/{idx}")
	public String shelterDetail(@PathVariable int idx) {
		return "user/shelter/shelterDetail";
	}
}
