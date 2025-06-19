package com.animal.api.shelter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.common.model.ErrorResponseDTO;
import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.shelter.model.response.AllShelterListDTO;
import com.animal.api.shelter.service.UserShelterService;

/**
 * 사용자 기준 보호시설 관련 컨트롤러 클래스
 * 
 * @author Rege-97
 * @since 2025-06-19
 * @see com.animal.api.shelter.model.response.AllShelterListDTO
 */
@RestController
@RequestMapping("/api/shelters")
public class UserShelterController {

	@Autowired
	private UserShelterService service;

	/**
	 * 보호시설 조회 메서드
	 * 
	 * @param cp 현재 페이지
	 * @return 조회된 보호소의 리스트
	 */
	@GetMapping
	public ResponseEntity<?> getShelters(@RequestParam(value = "cp", defaultValue = "0") int cp) {
		int listSize = 3;

		List<AllShelterListDTO> shelterList = service.getAllShelters(listSize, cp);

		if (shelterList == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "잘못된 접근입니다."));
		} else if (shelterList.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "조회된 데이터가 없습니다."));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkResponseDTO<List<AllShelterListDTO>>(200, "조회 성공", shelterList));
		}
	}

}
