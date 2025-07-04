package com.animal.api.index.volunteer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.index.volunteer.model.response.MainVolunteerCardResponseDTO;
import com.animal.api.index.volunteer.service.MainVolunteerService;

@RestController
@RequestMapping("/api/main/volunteers")
public class MainVolunteerController {
	
	private final MainVolunteerService mainService;
	
	@Autowired
	public MainVolunteerController(MainVolunteerService mainService) {
		this.mainService = mainService;
	}
	
	@GetMapping("/recent")
	public ResponseEntity<OkResponseDTO<List<MainVolunteerCardResponseDTO>>> getRecentVolunteers() {
	    List<MainVolunteerCardResponseDTO> list = mainService.getRecentVolunteers();
	    return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<List<MainVolunteerCardResponseDTO>>(200, "봉사 최근 목록 조회 성공", list));
	}
	
}
