package com.animal.api.mypage.donation.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.auth.model.response.LoginResponseDTO;
import com.animal.api.common.aop.util.SessionUtils;
import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.mypage.donation.model.response.DonationListResponseDTO;
import com.animal.api.mypage.donation.service.DonationService;

/**
 * 마이페이지 기부내역 조회 컨트롤러
 * 
 * @author Whistler95
 * @since 2025-06-25
 */
@RestController
@RequestMapping("/api/mypage/donation")
public class DonationController {

	@Autowired
	private DonationService donationService;

	/**
	 * 내가 기부한 내역 정보 조회 메서드
	 * 
	 * @param requset 로그인한 사용자의 정보
	 * @return 로그인한 사용자의 기부내역 목록 정보
	 */
	@GetMapping
	public ResponseEntity<?> DonationScrren(HttpServletRequest requset) {

		LoginResponseDTO loginUser = SessionUtils.getLoginUser(requset);

		if (loginUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new OkResponseDTO<>(401, "로그인이 필요합니다", null));
		}

		List<DonationListResponseDTO> list = donationService.getMyDonationList(loginUser.getIdx());
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(new OkResponseDTO<List<DonationListResponseDTO>>(200, list == null || list.isEmpty() ? "나의 기부 내역이 없습니다" : "기부 내역 조회 성공", list));
	}
}
