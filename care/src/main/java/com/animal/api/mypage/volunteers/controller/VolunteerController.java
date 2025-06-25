package com.animal.api.mypage.volunteers.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.animal.api.auth.model.response.LoginResponseDTO;
import com.animal.api.common.aop.util.SessionUtils;
import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.mypage.volunteers.model.request.VolunteerReviewWriteRequestDTO;
import com.animal.api.mypage.volunteers.model.response.VolunteerDetailResponseDTO;
import com.animal.api.mypage.volunteers.model.response.VolunteerListResponseDTO;
import com.animal.api.mypage.volunteers.service.VolunteerService;

/**
 * 마이페이지 봉사내역 컨트롤러
 * 
 * @author Whistler95
 * @since 2025-06-24
 */
@RestController
@RequestMapping("/api/mypage/volunteers")
public class VolunteerController {
	@Autowired
	private VolunteerService volunteerService;

	/**
	 * 내 봉사 내역 리스트 메서드
	 * 
	 * @param request 로그인한 유저 정보
	 * @return 유저정보의 봉사 내역 리스트
	 */
	@GetMapping
	public ResponseEntity<?> getMyVolunteerHistory(HttpServletRequest request) {

		LoginResponseDTO loginUser = SessionUtils.getLoginUser(request);

		if (loginUser == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new OkResponseDTO<>(401, "로그인 정보가 없습니다", null));
		}

		List<VolunteerListResponseDTO> list = volunteerService.getVolunteerListByUserIdx(loginUser.getIdx());

		return ResponseEntity.status(HttpStatus.OK)
				.body(new OkResponseDTO<List<VolunteerListResponseDTO>>(200, "내 봉사 내역 조회 성공", list));
	}

	/**
	 * 봉사 상세 내역 정보 메서드
	 * 
	 * @param volunteerRequestIdx 봉사게시글 고유번호
	 * @param request             로그인한 사용자 정보
	 * @return 해당 게시물의 사용자와 봉사게시글의 상세 내역
	 */
	@GetMapping("/{volunteerRequestIdx}")
	public ResponseEntity<?> getVolunteerDetail(@PathVariable int volunteerRequestIdx, HttpServletRequest request) {

		LoginResponseDTO loginUser = SessionUtils.getLoginUser(request);

		if (loginUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new OkResponseDTO<>(401, "로그인 정보가 없습니다", null));
		}

		VolunteerDetailResponseDTO detail = volunteerService.getVolunteerDetailByRequestIdx(volunteerRequestIdx);

		if (detail == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new OkResponseDTO<>(404, "봉사 상세 정보를 찾을 수 없습니다", null));
		}

		return ResponseEntity.status(HttpStatus.OK)
				.body(new OkResponseDTO<VolunteerDetailResponseDTO>(200, "봉사 상세 조회 성공", detail));
	}

	/**
	 * 후기 작성 메서드
	 * 
	 * @param dto     게시글의 IDX, 후기 본문
	 * @param image   사진 첨부 내역
	 * @param request 로그인한 사용자의 정보
	 * @return 후기 작성 완료
	 */
	@PostMapping("/reviews")
	public ResponseEntity<?> writeVolunteerReview(HttpServletRequest request,
			@RequestParam("volunteerRequestIdx") int volunteerRequestIdx, @RequestParam("content") String content,
			@RequestParam(value = "image", required = false) MultipartFile image) {

		LoginResponseDTO loginUser = SessionUtils.getLoginUser(request);
		
		if (loginUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new OkResponseDTO<>(401, "로그인이 필요합니다", null));
		}

		VolunteerReviewWriteRequestDTO dto = new VolunteerReviewWriteRequestDTO();
		dto.setVolunteerRequestIdx(volunteerRequestIdx);
		dto.setContent(content);
		
		boolean success = volunteerService.writeVolunteerReview(loginUser.getIdx(), dto, image);

		return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<>(200, "후기 작성 완료", success));
	}

}
