package com.animal.api.find.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/find")
public class FindViewController {

	@GetMapping
	public String findingScreen(Model model) {
		model.addAttribute("recaptchaSiteKey", "6LfDx2grAAAAAKaMfsjIo7JaJrGEkaNFeYLlC4GB");
		return "user/auth/find/finding";
	}
	
	@GetMapping ("/id")
	public String idResult() {
		return "user/auth/find/idResult/idResult";
	}
}
