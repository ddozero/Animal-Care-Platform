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

	@GetMapping("/{shelterIdx}/volunteers/{volunteerIdx}")
	public String shelterVolunteerDetail(@PathVariable int shelterIdx, @PathVariable int volunteerIdx) {
		return "user/shelter/shelterVolunteerDetail";
	}

	@GetMapping("/{shelterIdx}/animals/{animalIdx}")
	public String shelterAnimalDetail(@PathVariable int shelterIdx, @PathVariable int animalIdx) {
		return "user/shelter/shelterAnimalDetail";
	}

	@GetMapping("/{shelterIdx}/boards/{boardIdx}")
	public String shelterBoardDetail(@PathVariable int shelterIdx, @PathVariable int boardIdx) {
		return "user/shelter/shelterBoardDetail";
	}
}
