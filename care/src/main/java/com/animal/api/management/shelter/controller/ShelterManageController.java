package com.animal.api.management.shelter.controller;

import java.util.List;

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
import org.springframework.web.server.ResponseStatusException;

import com.animal.api.auth.model.response.LoginResponseDTO;
import com.animal.api.common.model.ErrorResponseDTO;
import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.management.shelter.model.request.ManageVolunteerReplyRequestDTO;
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
@RequestMapping("/api/management/shelter")
public class ShelterManageController {

	@Autowired
	private ShelterManageService shelterService;

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
	public ResponseEntity<?> updateShelterInfo(@Valid @RequestBody ShelterInfoUpdateRequestDTO dto,
			HttpSession session) {

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
	 * @param cp      현재 페이지 번호
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

	/**
	 * 해당 보호시설 봉사 리뷰 글에 대한 답글 작성 메서드
	 * 
	 * @param dto     봉사 리뷰 답변글
	 * @param session 로그인 검증 세션
	 * 
	 * @return 해당 보호시설 봉사 리뷰글 답글
	 */
	@PostMapping("/reviews/volunteer")
	public ResponseEntity<?> addVolunteerReviewApply(@Valid @RequestBody ManageVolunteerReplyRequestDTO dto,
			HttpSession session) {

		LoginResponseDTO loginUser = shelterUserCheck(session);
		int userIdx = loginUser.getIdx();
		dto.setUserIdx(userIdx);

		int result = shelterService.addVolunteerReviewApply(dto);

		if (result == shelterService.NOT_REVIEW) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(404, "삭제된 리뷰에는 답글 달 수 없음"));
		} else if (result == shelterService.REPLY_OK) {
			return ResponseEntity.ok(new OkResponseDTO<>(201, "리뷰 답글 등록 성공", null));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "잘못된 접근"));
		}
	}

	/**
	 * 해당 보호시설 봉사 리뷰 글에 대한 답글 작성 수정 메서드
	 * 
	 * @param dto     봉사 리뷰 답변글
	 * @param session 로그인 검증 세션
	 * 
	 * @return 해당 보호시설 봉사 리뷰글 답글 수정
	 */
	@PutMapping("/reviews/volunteer")
	public ResponseEntity<?> updateVolunterReviewApply(@Valid @RequestBody ManageVolunteerReplyRequestDTO dto,
			HttpSession session) {

		LoginResponseDTO loginUser = shelterUserCheck(session);

		int userIdx = loginUser.getIdx();
		dto.setUserIdx(userIdx);

		int count = shelterService.updateVolunteerReviewApply(dto);
		if (count > 0) {
			return ResponseEntity.ok(new OkResponseDTO<>(200, "봉사 리뷰 답글 수정 성공", null));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "잘못된 접근"));
		}
	}

	/**
	 * 해당 보호시설 봉사 리뷰 글에 대한 답글 작성 삭제 메서드
	 * 
	 * @param reviewIdx 봉사 리뷰 글 번호
	 * @param session   로그인 검증 세션
	 * 
	 * @return 해당 보호시설 봉사 리뷰글 답글 삭제
	 */
	@DeleteMapping("/reviews/volunteer/{reviewIdx}")
	public ResponseEntity<?> deleteVolunteerReviewApply(@PathVariable int reviewIdx, HttpSession session) {

		LoginResponseDTO loginUser = shelterUserCheck(session);

		int userIdx = loginUser.getIdx();

		int result = shelterService.deleteVolunteerReviewApply(reviewIdx);
		if (result > 0) {
			return ResponseEntity.ok(new OkResponseDTO<>(200, "봉사 리뷰 답글 삭제 성공", null));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "잘못된 접근"));
		}
	}

	/**
	 * (공통) 로그인 및 보호시설 사용자 검증 메서드
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

}
