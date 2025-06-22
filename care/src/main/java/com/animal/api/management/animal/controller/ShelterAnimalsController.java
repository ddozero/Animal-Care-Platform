package com.animal.api.management.animal.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.auth.model.response.LoginResponseDTO;
import com.animal.api.common.model.ErrorResponseDTO;
import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.management.animal.model.request.AnimalInsertRequestDTO;
import com.animal.api.management.animal.model.request.AnimalUpdateRequestDTO;
import com.animal.api.management.animal.model.response.AnimalAddShelterInfoResponseDTO;
import com.animal.api.management.animal.service.ShelterAnimalsService;

/**
 * 보호시설의 관리 페이지에서 유기동물에 관련되어 있는 컨트롤러 클래스
 * 
 * @author Rege-97
 * @since 2026-06-22
 * @see com.animal.api.management.animal.model.response.AnimalAddShelterInfoResponseDTO
 * @see com.animal.api.management.animal.model.response.AnimalInsertRequestDTO
 */

@RestController
@RequestMapping("api/management/animals")
public class ShelterAnimalsController {

	@Autowired
	private ShelterAnimalsService service;

	/**
	 * 유기동물 등록 시 내 보호시설 확인 메서드
	 * 
	 * @param session 로그인 정보 검증을 위한 세션
	 * @return 로그인된 보호소 정보 반환
	 */
	@GetMapping("/shelter")
	public ResponseEntity<?> getShelterProfile(HttpSession session) {
		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");

		if (loginUser == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginUser.getUserTypeIdx() != 2) { // 보호시설 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "보호시설 회원만 접근 가능합니다."));
		}

		AnimalAddShelterInfoResponseDTO dto = service.getShelterProfile(loginUser.getIdx());

		if (dto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "해당 보호시설을 찾을 수 없습니다."));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkResponseDTO<AnimalAddShelterInfoResponseDTO>(200, "조회 성공", dto));
		}
	}

	/**
	 * 유기동물 등록 관련 메서드
	 * 
	 * @param dto     유기동물 정보 폼 데이터
	 * @param session 로그인 검증을 위한 세션
	 * @return 등록성공 또는 실패 메세지
	 */
	@PostMapping
	public ResponseEntity<?> insertAnimal(@Valid @RequestBody AnimalInsertRequestDTO dto, HttpSession session) {
		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");

		if (loginUser == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginUser.getUserTypeIdx() != 2) { // 보호시설 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "보호시설 회원만 접근 가능합니다."));
		}

		int result = service.insertAnimal(dto);

		if (result == service.POST_SUCCESS) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new OkResponseDTO<Void>(201, "유기동물 등록 성공", null));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "유기동물 등록 실패"));
		}
	}

	@PutMapping("/{idx}")
	public ResponseEntity<?> updateAnimal(@Valid @RequestBody AnimalUpdateRequestDTO dto, HttpSession session) {
		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");

		if (loginUser == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginUser.getUserTypeIdx() != 2) { // 보호시설 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "보호시설 회원만 접근 가능합니다."));
		}

		int result = service.updateAnimal(dto);

		if (result == service.UPDATE_SUCCESS) {
			return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<Void>(200, "유기동물 수정 성공", null));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "유기동물 수정 실패"));
		}
	}

}
