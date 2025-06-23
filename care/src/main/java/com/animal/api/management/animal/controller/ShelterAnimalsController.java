package com.animal.api.management.animal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.animal.api.auth.model.response.LoginResponseDTO;
import com.animal.api.common.model.ErrorResponseDTO;
import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.management.animal.model.request.AnimalInsertRequestDTO;
import com.animal.api.management.animal.model.request.AnimalUpdateRequestDTO;
import com.animal.api.management.animal.model.response.AdoptionConsultListResponseDTO;
import com.animal.api.management.animal.model.response.AnimalAddShelterInfoResponseDTO;
import com.animal.api.management.animal.service.ShelterAnimalsService;

/**
 * 보호시설의 관리 페이지에서 유기동물에 관련되어 있는 컨트롤러 클래스
 * 
 * @author Rege-97
 * @since 2026-06-23
 * @see com.animal.api.management.animal.model.response.AnimalAddShelterInfoResponseDTO
 * @see com.animal.api.management.animal.model.request.AnimalInsertRequestDTO
 * @see com.animal.api.management.animal.model.request.AnimalUpdateRequestDTO
 * @see com.animal.api.management.animal.model.response.AdoptionConsultListResponseDTO
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
	 * @return 생성된 idx
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
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("createdIdx", dto.getIdx());
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new OkResponseDTO<Map<String, Integer>>(201, "유기동물 등록 성공", map));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "유기동물 등록 실패"));
		}
	}

	/**
	 * 유기동물 이미지 업로드 메서드
	 * 
	 * @param files 프론트 영역에서 받은 유기동물 이미지
	 * @param idx   유기동물이 생성되면서 생긴 관리번호
	 * @return 파일 업로드 성공 또는 실패
	 */
	@PostMapping("/upload/{idx}")
	public ResponseEntity<?> uploadAnimalImage(MultipartFile[] files, @PathVariable int idx) {
		int result = service.uploadAnimalImage(files, idx);
		if (result == service.UPLOAD_SUCCESS) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new OkResponseDTO<Void>(201, "이미지 업로드 성공", null));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "이미지 업로드 실패"));
		}
	}

	/**
	 * 유기동물의 정보를 수정하는 메서드
	 * 
	 * @param idx     유기동물 관리번호
	 * @param dto     수정될 내용을 담은 폼 데이터
	 * @param session 로그인 검증을 위한 세션
	 * @return 성공 또는 실패 메세지
	 */
	@PutMapping("/{idx}")
	public ResponseEntity<?> updateAnimal(@PathVariable int idx, @Valid @RequestBody AnimalUpdateRequestDTO dto,
			HttpSession session) {
		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");

		if (loginUser == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginUser.getUserTypeIdx() != 2) { // 보호시설 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "보호시설 회원만 접근 가능합니다."));
		}

		int userIdx = service.getAnimalShelter(idx); // 유기동물이 소속된 보호시설 조회

		if (userIdx == service.NOT_ANIMAL) { // 유기동물 DB 데이터 유무 검증
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponseDTO(404, "해당 유기동물이 존재하지 않거나 삭제되었습니다."));
		}

		if (loginUser.getIdx() != userIdx) { // 로그인된 보호시설 유기동물 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "해당 보호시설의 유기동물이 아닙니다."));
		}

		int result = service.updateAnimal(dto);

		if (result == service.UPDATE_SUCCESS) {
			return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<Void>(200, "유기동물 수정 성공", null));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "유기동물 수정 실패"));
		}
	}

	/**
	 * 유기동물의 데이터를 삭제하는 메서드
	 * 
	 * @param idx     유기동물 관리번호
	 * @param session 로그인 검증을 위한 세션
	 * @return 성공 또는 실패 메세지
	 */
	@DeleteMapping("/{idx}")
	public ResponseEntity<?> deleteAnimal(@PathVariable int idx, HttpSession session) {
		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");

		if (loginUser == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginUser.getUserTypeIdx() != 2) { // 보호시설 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "보호시설 회원만 접근 가능합니다."));
		}

		int userIdx = service.getAnimalShelter(idx); // 유기동물이 소속된 보호시설 조회

		if (userIdx == service.NOT_ANIMAL) { // 유기동물 DB 데이터 유무 검증
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponseDTO(404, "해당 유기동물이 존재하지 않거나 이미 삭제되었습니다."));
		}

		if (loginUser.getIdx() != userIdx) { // 로그인된 보호시설 유기동물 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "해당 보호시설의 유기동물이 아닙니다."));
		}

		int result = service.deleteAnimal(idx);

		if (result == service.DELETE_SUCCESS) {
			return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<Void>(200, "유기동물 삭제 성공", null));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "유기동물 삭제 실패"));
		}
	}

	/**
	 * 해당 보호시설의 입양 신청 내역 리스트를 조회하는 메서드
	 * @param cp 현재 페이지
	 * @param session 로그인 검증을 위한 세션
	 * @return 입양 상담 신청내역 리스트
	 */
	@GetMapping("/adoptions")
	public ResponseEntity<?> getAdoptionConsultList(@RequestParam(value = "cp", defaultValue = "0") int cp,
			HttpSession session) {
		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");

		if (loginUser == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginUser.getUserTypeIdx() != 2) { // 보호시설 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "보호시설 회원만 접근 가능합니다."));
		}

		int listSize = 3;

		List<AdoptionConsultListResponseDTO> consultList = service.getAdoptionConsultList(loginUser.getIdx(), listSize,
				cp);

		if (consultList == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "잘못된 요청 입니다."));
		} else if (consultList.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "조회된 데이터가 없습니다."));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkResponseDTO<List<AdoptionConsultListResponseDTO>>(200, "조회 성공", consultList));
		}
	}

}
