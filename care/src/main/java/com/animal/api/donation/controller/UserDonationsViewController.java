package com.animal.api.donation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/donations")
public class UserDonationsViewController {

	@GetMapping
	public String donationList() {
		return "user/donation/donationList";
	}

	@GetMapping("/{idx}")
	public String donationDetail(@PathVariable int idx) {
		return "user/donation/donationDetail";
	}
}
