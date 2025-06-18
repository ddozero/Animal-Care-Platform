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
import com.animal.api.common.model.OkResponseDTO;

/**
 * 사용자 기준 유기동물 관련 컨트롤러 클래스
 * @author Rege-97
 * @since 2025-06-18
 * @see com.animal.api.animal.model.response.AllAnimalListResponseDTO
 * */
@RestController
@RequestMapping("/api/animals")
public class UserAnimalController {

	@Autowired
	private UserAnimalService service;

	/**
	 * 사용자의 유기동물 조회 메서드
	 * @param 현재 보고있는 페이지 번호
	 * @return 사용자에게 보여줄 유기동물 리스트
	 */
	@GetMapping
	public ResponseEntity<?> getAnimals(@RequestParam(value = "cp", defaultValue = "0") int cp) {
		int listSize = 3; 

		// 현재 페이지를 페이징 쿼리에 맞게 가공
		if (cp == 1) {
			cp = 0;
		} else {
			cp = (cp - 1) * listSize;
		}

		List<AllAnimalListResponseDTO> animalList = service.getAllAnimals(listSize, cp);

		return ResponseEntity.status(HttpStatus.OK)
				.body(new OkResponseDTO<List<AllAnimalListResponseDTO>>(200, "조회 성공", animalList));
	}

}
