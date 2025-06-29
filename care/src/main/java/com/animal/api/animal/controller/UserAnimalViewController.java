package com.animal.api.animal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/animals")
public class UserAnimalViewController {
	
	@GetMapping
	public String animalList() {
		return "user/animal/animalList";
	}
	
	@GetMapping("/{idx}")
	public String animalDetail(@PathVariable int idx) {
		return "user/animal/animalDetail";
	}
	
	@GetMapping("/{idx}/adoption")
	public String animalAdoption(@PathVariable int idx) {
		return "user/animal/animalAdoption";
	}
}
