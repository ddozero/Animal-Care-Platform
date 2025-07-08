package com.animal.api.admin.donation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/donations")
public class AdminDonationViewController {

	@GetMapping
	public String donationList() {
		return "/admin/donation/donationList";
	}

	@GetMapping("{idx}")
	public String donationDetail(@PathVariable int idx) {
		return "/admin/donation/donationDetail";
	}

	@GetMapping("add")
	public String donationAddForm() {
		return "/admin/donation/donationAddForm";
	}
}
