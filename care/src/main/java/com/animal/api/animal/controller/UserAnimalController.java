package com.animal.api.animal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.animal.model.response.AllAnimalListResponseDTO;
import com.animal.api.animal.service.UserAnimalService;
import com.animal.api.common.model.ErrorResponseDTO;
import com.animal.api.common.model.OkResponseDTO;

/**
 * 사용자 기준 유기동물 관련 컨트롤러 클래스
 * 
 * @author Rege-97
 * @since 2025-06-18
 * @see com.animal.api.animal.model.response.AllAnimalListResponseDTO
 */
@RestController
@RequestMapping("/api/animals")
public class UserAnimalController {

	@Autowired
	private UserAnimalService service;

	/**
	 * 사용자의 유기동물 조회 메서드
	 * 
	 * @param cp 현재 보고있는 페이지 번호
	 * @return 사용자에게 보여줄 유기동물 리스트
	 */
	@GetMapping
	public ResponseEntity<?> getAnimals(@RequestParam(value = "cp", defaultValue = "0") int cp,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "breed", required = false) String breed,
			@RequestParam(value = "gender", required = false) String gender,
			@RequestParam(value = "neuter", defaultValue = "0") int neuter,
			@RequestParam(value = "age", defaultValue = "0") int age,
			@RequestParam(value = "adoptionStatus", required = false) String adoptionStatus,
			@RequestParam(value = "personality", required = false) String personality,
			@RequestParam(value = "size", defaultValue = "0") int size,
			@RequestParam(value = "name", required = false) String name) {
		int listSize = 3;
		List<AllAnimalListResponseDTO> animalList = null;
		if (type != null || breed != null || gender != null || neuter != 0 || age != 0 || adoptionStatus != null
				|| personality != null || size != 0 || name != null) {
			animalList = service.searchAnimals(listSize, cp, type, breed, gender, neuter, age, adoptionStatus,
					personality, size, name);
		} else {
			animalList = service.getAllAnimals(listSize, cp);
		}

		if (animalList == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "잘못된 요청입니다"));
		} else if (animalList.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "조회된 데이터가 없습니다."));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkResponseDTO<List<AllAnimalListResponseDTO>>(200, "조회 성공", animalList));
		}
	}

}
