package com.animal.api.shelter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.shelter.service.UserShelterService;

@RestController
@RequestMapping("/api/shelters")
public class UserShelterController {

	@Autowired
	private UserShelterService service;
}
