package com.animal.api.management.dashboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.management.dashboard.service.ShelterDashboardService;

@RestController
@RequestMapping("/api/management/dashboards")
public class ShelterDashboardController {
	@Autowired
	private ShelterDashboardService service;
}
