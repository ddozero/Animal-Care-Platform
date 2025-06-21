package com.animal.api.management.shelter.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.auth.model.response.LoginResponseDTO;
import com.animal.api.common.model.ErrorResponseDTO;
import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.management.shelter.model.request.ShelterInfoUpdateRequestDTO;
import com.animal.api.management.shelter.model.response.AllManageShelterResponseDTO;
import com.animal.api.management.shelter.service.ShelterManageService;

/**
 * 보호시설 관리자 페이지의 보호시설관리 관련 컨트롤러 클래스
 * 
 * @author doyeong
 * @since 2025-06-21
 * @see com.animal.api.management.shelter.model.response.AllManageShelterDTO
 */
@RestController
@RequestMapping("api/management/shelter")
public class ShelterManageController {

	@Autowired
	private ShelterManageService shelterService;

	/**
	 * 
	 * @param session 로그인 검증 세션
	 * @return 로그인한 보호소 기본 정보
	 */
	@GetMapping
	public ResponseEntity<?> getShelterInfo(HttpSession session) {

		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");

		if (loginUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용가능"));
		}
		int userIdx = loginUser.getIdx();

		AllManageShelterResponseDTO dto = shelterService.getShelterInfo(userIdx);

		if (dto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "회원이 존재하지 않음"));
		} else {
			return ResponseEntity.ok(new OkResponseDTO<AllManageShelterResponseDTO>(200, "보호소 기본정보 조회 성공", dto));
		}
	}

	@PutMapping
	public ResponseEntity<?> updateShelterInfo(@RequestBody ShelterInfoUpdateRequestDTO dto, HttpSession session) {

		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");
		if (loginUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용가능"));
		}

		int userIdx = loginUser.getIdx();
		dto.setIdx(userIdx);
		int count = shelterService.updateSheterInfo(dto);
		if (count > 0) {
			AllManageShelterResponseDTO responseDto = shelterService.getShelterInfo(userIdx);
			return ResponseEntity.ok(new OkResponseDTO<AllManageShelterResponseDTO>(200, "기본정보 수정 성공", responseDto));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "기본정보 수정 실패"));
		}
	}

}
