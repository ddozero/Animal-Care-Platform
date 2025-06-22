package com.animal.api.management.animal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.management.animal.service.ShelterAnimalsService;

@RestController
@RequestMapping("api/management/animals")
public class ShelterAnimalsController {

	@Autowired
	private ShelterAnimalsService service;
}
