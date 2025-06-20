package com.animal.api.animal.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.animal.model.request.AdoptionSubmitReqestDTO;
import com.animal.api.animal.model.response.AdoptionAnimalResponseDTO;
import com.animal.api.animal.model.response.AllAnimalListResponseDTO;
import com.animal.api.animal.model.response.AnimalDetailResponseDTO;
import com.animal.api.animal.service.UserAnimalService;
import com.animal.api.auth.model.response.LoginResponseDTO;
import com.animal.api.common.model.ErrorResponseDTO;
import com.animal.api.common.model.OkResponseDTO;

/**
 * 사용자 기준 유기동물 관련 컨트롤러 클래스
 * 
 * @author Rege-97
 * @since 2025-06-20
 * @see com.animal.api.animal.model.response.AllAnimalListResponseDTO
 * @see com.animal.api.animal.model.response.AnimalDetailResponseDTO
 * @see com.animal.api.animal.model.response.AdoptionAnimalResponseDTO
 */
@RestController
@RequestMapping("/api/animals")
public class UserAnimalController {

	@Autowired
	private UserAnimalService service;

	/**
	 * 사용자의 유기동물 조회 메서드
	 * 
	 * @param cp             현재 보고있는 페이지 번호
	 * @param type           동물 유형
	 * @param breed          동물 품종
	 * @param gender         동물 성별
	 * @param neuter         중성화 여부
	 * @param age            동물 나이
	 * @param adoptionStatus 입양 상태
	 * @param personality    동물 성격
	 * @param size           동물 크기
	 * @param name           동물 이름
	 * 
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

	/**
	 * 유기동물의 상세 정보를 조회하는 메서드
	 * 
	 * @param idx 유기동물 관리번호
	 * @return 사용자에게 보여줄 유기동물과 보호소 정보
	 */
	@GetMapping("/{idx}")
	public ResponseEntity<?> getAnimalDetail(@PathVariable int idx) {
		AnimalDetailResponseDTO dto = service.getAnimalDetail(idx);

		if (dto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "해당 동물이 존재하지 않습니다."));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkResponseDTO<AnimalDetailResponseDTO>(200, "조회 성공", dto));
		}
	}

	/**
	 * 유기동물의 입양 폼 페이지로 진입 했을 시 선택한 유기동물의 정보를 조회하는 메서드
	 * 
	 * @param idx     유기동물 관리번호
	 * @param session 로그인 검증을 위한 세션
	 * @return 유기동물의 정보
	 */
	@GetMapping("/{idx}/adoption")
	public ResponseEntity<?> getAdoptionInfo(@PathVariable int idx, HttpSession session) {
		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");

		if (loginUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		AdoptionAnimalResponseDTO dto = service.getAdoptionInfo(idx);

		if (dto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "해당 동물이 존재하지 않습니다."));
		} else {
			if (dto.getAdoptionStatusIdx() != 1) { // 1은 입양 가능 상태
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ErrorResponseDTO(400, "해당 동물은 입양할 수 없습니다."));
			} else {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new OkResponseDTO<AdoptionAnimalResponseDTO>(200, "조회 성공", dto));
			}
		}
	}

	/**
	 * 유기동물의 입양 폼 페이지에서 입양을 신청하는 메서드
	 * 
	 * @param dto     상담 신청 폼의 내용
	 * @param idx     유기동물 관리번호
	 * @param session 로그인 검증을 위한 세션
	 * @return 신청 성공 메세지
	 */
	@PostMapping("/{idx}/adoption")
	public ResponseEntity<?> submitAdoption(@RequestBody AdoptionSubmitReqestDTO dto, @PathVariable int idx,
			HttpSession session) {
		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");

		if (loginUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		int result = service.submitAdoption(dto);

		if (result == service.RESERVATION_COMPLETED) {
			return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<Void>(200, "입양 상담 신청 성공", null));
		} else if (result == service.RESERVATION_UNAVAILABLE) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseDTO(409, "입양 상담 신청 가능 상태가 아닙니다."));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "잘못된 접근입니다."));
		}
	}

}
