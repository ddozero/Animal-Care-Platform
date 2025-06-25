package com.animal.api.mypage.adoption.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.animal.api.auth.model.response.LoginResponseDTO;
import com.animal.api.common.aop.util.SessionUtils;
import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.mypage.adoption.model.request.AdoptionReviewWriteRequestDTO;
import com.animal.api.mypage.adoption.model.response.AdoptionDetailResponseDTO;
import com.animal.api.mypage.adoption.model.response.AdoptionListResponseDTO;
import com.animal.api.mypage.adoption.service.AdoptionService;

/**
 * 마이페이지 - 입양 내역 관련 컨트롤러 
 * 입양 신청 내역 조회, 상세 조회, 후기 작성 기능 제공
 * 
 * @author Whislter95
 * @since 2025-06-24
 *
 */
@RestController
@RequestMapping("/api/mypage/adoptions")
public class AdoptionController {

	@Autowired
	private AdoptionService adoptionService;

	/**
	 * 내 입양 내역 리스트 조회 API
	 *
	 * @param request 현재 로그인한 사용자의 세션 정보
	 * @return 해당 사용자의 입양 상담 신청 내역 리스트 반환
	 */
	@GetMapping
	public ResponseEntity<?> getMyAdoptionHistory(HttpServletRequest request) {
		LoginResponseDTO loginUser = SessionUtils.getLoginUser(request);

		if (loginUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new OkResponseDTO<>(401, "로그인 정보가 없습니다", null));
		}

		List<AdoptionListResponseDTO> list = adoptionService.getAdoptionListByUserIdx(loginUser.getIdx());
		return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<>(200, "내 입양 내역 조회 성공", list));
	}

	/**
	 * 입양 상세 내역 조회 API
	 *
	 * @param adoptionConsultIdx 입양 상담 신청 고유 번호
	 * @param request            현재 로그인한 사용자의 세션 정보
	 * @return 유기동물 정보, 입양 상태, 보호소 정보 등 상세 정보 반환
	 */
	@GetMapping("/{adoptionConsultIdx}")
	public ResponseEntity<?> getAdoptionDetail(@PathVariable int adoptionConsultIdx, HttpServletRequest request) {
		LoginResponseDTO loginUser = SessionUtils.getLoginUser(request);

		if (loginUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new OkResponseDTO<>(401, "로그인 정보가 없습니다", null));
		}

		AdoptionDetailResponseDTO detail = adoptionService.getAdoptionDetailByConsultIdx(adoptionConsultIdx);

		if (detail == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new OkResponseDTO<>(404, "입양 상세 정보를 찾을 수 없습니다", null));
		}

		return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<>(200, "입양 상세 조회 성공", detail));
	}

	/**
	 * 입양 후기 작성 API
	 *
	 * @param dto     입양 후기 작성 요청 데이터 (입양 상담 IDX + 후기 내용)
	 * @param image   첨부 이미지 (선택 사항)
	 * @param request 현재 로그인한 사용자의 세션 정보
	 * @return 후기 작성 성공 메시지
	 */
	@PostMapping("/reviews")
	public ResponseEntity<?> writeAdoptionReview(@RequestPart("dto") AdoptionReviewWriteRequestDTO dto,
			@RequestPart(value = "image", required = false) MultipartFile image, HttpServletRequest request) {

		LoginResponseDTO loginUser = SessionUtils.getLoginUser(request);

		if (loginUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new OkResponseDTO<>(401, "로그인이 필요합니다", null));
		}

		adoptionService.writeAdoptionReview(loginUser.getIdx(), dto, image);

		return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<>(200, "입양 후기 작성 완료", null));
	}
}