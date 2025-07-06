package com.animal.api.management.volunteers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/management/volunteers")
public class ShelterVolunteersViewController {
	@GetMapping
	public String shelterVolunteerList() {
		return "management/volunteers/shelterVolunteersList";
	}

	@GetMapping("/{idx}")
	public String shelterVolunteerDetail(@PathVariable int idx) {
		return "management/volunteers/shelterVolunteerDetail";
	}

	@GetMapping("/form")
	public String shelterVolunteerForm() {
		return "management/volunteers/shelterVolunteerForm";
	}

	@GetMapping("/{volunteerIdx}/applications")
	public String shelterApplicationList(@PathVariable int volunteerIdx) {
		return "management/volunteers/shelterVolunteersSubmitList";
	}

}
