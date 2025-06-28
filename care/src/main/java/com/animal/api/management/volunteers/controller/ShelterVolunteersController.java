package com.animal.api.management.volunteers.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.management.volunteers.model.response.ShelterVolunteersListResponseDTO;
import com.animal.api.management.volunteers.service.ShelterVolunteersService;

/**
 * 보호시설 기준 봉사 관련 컨트롤러 클래스
 * 
 * @author consgary
 * @since 2025.06.28
 * @see
 */

@RestController
@RequestMapping("/api/management/volunteers")
public class ShelterVolunteersController {

	@Autowired
	private ShelterVolunteersService service;

	@GetMapping
	public ResponseEntity<?> getShelterVolunteers(@RequestParam(value = "cp", defaultValue = "0") int cp) {
		int listSize = 3;

		List<ShelterVolunteersListResponseDTO> shelterVolunteersList = service.getShelterAllVolunteers(listSize, cp,
				listSize);

		return null;

	}
}
