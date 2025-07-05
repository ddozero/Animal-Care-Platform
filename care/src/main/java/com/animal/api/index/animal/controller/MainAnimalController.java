package com.animal.api.index.animal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.index.animal.model.response.MainAnimalResponseDTO;
import com.animal.api.index.animal.service.MainAnimalService;

@RestController
@RequestMapping("/api/main/animals")
public class MainAnimalController {
	
	private final MainAnimalService animalService;
	
	@Autowired
	public MainAnimalController(MainAnimalService animalService) {
		this.animalService = animalService;
	}

	@GetMapping("/recent")
	public ResponseEntity<OkResponseDTO<List<MainAnimalResponseDTO>>> getRecentAnimals(){
		List<MainAnimalResponseDTO> animals = animalService.getRecentAnimals(5);
		return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<List<MainAnimalResponseDTO>>(200,"오늘의 유기동물 조회 성공", animals));
	}
}
