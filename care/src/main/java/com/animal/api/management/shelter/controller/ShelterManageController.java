package com.animal.api.management.shelter.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.animal.api.auth.model.response.LoginResponseDTO;
import com.animal.api.common.model.ErrorResponseDTO;
import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.management.shelter.model.request.ShelterInfoUpdateRequestDTO;
import com.animal.api.management.shelter.model.response.AllManageShelterResponseDTO;
import com.animal.api.management.shelter.model.response.ManageAdoptionReviewResponseDTO;
import com.animal.api.management.shelter.model.response.ManageVolunteerReviewResponseDTO;
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
	 * 로그인 및 보호시설 사용자 검증 메서드
	 * 
	 * @param session 로그인 검증 세션
	 * 
	 * @return 보호시설 계정으로 로그인한 관리자
	 */
	public LoginResponseDTO shelterUserCheck(HttpSession session) {
		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");

		if (loginUser == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인 후 이용가능");
		}
		if (loginUser.getUserTypeIdx() != 2) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "보호소 사용자만 접근 가능");
		}

		return loginUser;
	}

	/**
	 * 보호시설 기본정보 조회 메서드
	 * 
	 * @param session 로그인 검증 세션
	 * 
	 * @return 로그인한 보호소 기본 정보
	 */
	@GetMapping
	public ResponseEntity<?> getShelterInfo(HttpSession session) {

		LoginResponseDTO loginUser = shelterUserCheck(session);

		int userIdx = loginUser.getIdx();

		AllManageShelterResponseDTO dto = shelterService.getShelterInfo(userIdx);

		if (dto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "존재하지 않는 보호소"));
		} else {
			return ResponseEntity.ok(new OkResponseDTO<AllManageShelterResponseDTO>(200, "보호소 기본정보 조회 성공", dto));
		}
	}

	/**
	 * 보호시설 기본정보 수정 메서드
	 * 
	 * @param dto     보호시설 기본정보
	 * @param session 로그인 검증 세션
	 * 
	 * @return 수정에 따른 메세지
	 */
	@PutMapping
	public ResponseEntity<?> updateShelterInfo(@RequestBody ShelterInfoUpdateRequestDTO dto, HttpSession session) {

		LoginResponseDTO loginUser = shelterUserCheck(session);

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

	/**
	 * 해당 보호시설 봉사 리뷰 조회 메서드
	 * 
	 * @param cp      현재 페이지 번호
	 * @param session 로그인 검증 세션
	 * 
	 * @return 로그인한 보호시설의 사용자 봉사 리뷰 조회
	 */
	@GetMapping("/reviews/volunteer")
	public ResponseEntity<?> getVolunteerReview(@RequestParam(value = "cp", defaultValue = "0") int cp,
			HttpSession session) {

		int listSize = 5;
		if (cp == 0) {
			cp = 1;
		} else {
			cp = (cp - 1) * listSize;
		}

		LoginResponseDTO loginUser = shelterUserCheck(session);

		int userIdx = loginUser.getIdx();

		List<ManageVolunteerReviewResponseDTO> reviewList = shelterService.getVolunteerReview(listSize, cp, userIdx);

		if (reviewList == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "리뷰글이 존재하지 않음"));
		} else if (reviewList.size() == 0) {
			return ResponseEntity.ok(new OkResponseDTO<>(200, "등록된 리뷰가 없습니다", reviewList));
		} else {
			return ResponseEntity.ok(new OkResponseDTO<>(200, "리뷰 조회 성공", reviewList));
		}
	}
	
	/**
	 * 해당 보호시설 입양 리뷰 조회 메서드
	 * 
	 * @param cp 현재 페이지 번호
	 * @param session 로그인 검증 세션
	 * 
	 * @return 로그인한 보호시설의 사용자 입양 리뷰 조회
	 */
	@GetMapping("/reviews/adoption")
	public ResponseEntity<?> getAdoptionReview(@RequestParam(value = "cp", defaultValue = "0") int cp,
			HttpSession session) {

		int listSize = 5;
		if (cp == 0) {
			cp = 1;
		} else {
			cp = (cp - 1) * listSize;
		}

		LoginResponseDTO loginUser = shelterUserCheck(session);

		int userIdx = loginUser.getIdx();

		List<ManageAdoptionReviewResponseDTO> reviewList = shelterService.getAdoptionReview(listSize, cp, userIdx);

		if (reviewList == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "리뷰글이 존재하지 않음"));
		} else if (reviewList.size() == 0) {
			return ResponseEntity.ok(new OkResponseDTO<>(200, "등록된 리뷰가 없습니다", reviewList));
		} else {
			return ResponseEntity.ok(new OkResponseDTO<>(200, "리뷰 조회 성공", reviewList));
		}

	}

}
