package com.animal.api.management.animal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/management/animals")
public class ShelterAnimalsViewController {
	
	@GetMapping
	public String managementAnimalList() {
		return "management/animal/managementAnimalList";
	}
	
	@GetMapping("/{idx}")
	public String managementAnimalDetail(@PathVariable int idx) {
		return "management/animal/managementAnimalDetail";
	}
	
	@GetMapping("/form")
	public String managementAnimalAdd() {
		return "management/animal/managementAnimalAdd";
	}
	
	@GetMapping("/adoptions")
	public String managementAdoption() {
		return "management/animal/managementAdoption";
	}
}
