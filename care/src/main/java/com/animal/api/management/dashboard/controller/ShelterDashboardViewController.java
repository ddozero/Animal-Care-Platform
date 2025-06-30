package com.animal.api.management.dashboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/management/dashboards")
public class ShelterDashboardViewController {
	@GetMapping
	public String managementDashboard() {
		return "management/dashboard/managementDashboard";
	}
}
