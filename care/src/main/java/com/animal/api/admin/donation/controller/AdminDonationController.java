package com.animal.api.admin.donation.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.animal.api.admin.donation.model.response.AdminAllDonationResponseDTO;
import com.animal.api.admin.donation.service.AdminDonationService;
import com.animal.api.auth.model.response.LoginResponseDTO;
import com.animal.api.common.model.ErrorResponseDTO;
import com.animal.api.common.model.OkResponseDTO;

@RestController
@RequestMapping("api/admin/donation")
public class AdminDonationController {

	@Autowired
	private AdminDonationService adminDonationService;

	public ResponseEntity<?> getAdminDonationList(@RequestParam(value = "cp", defaultValue = "") int cp,
			HttpSession session) {

		LoginResponseDTO loginUser = shelterUserCheck(session); // 로그인 여부, 관리자 회원 검증

		int userIdx = loginUser.getIdx();

		int listSize = 5;
		if (cp == 0) {
			cp = 1;
		} else {
			cp = (cp - 1) * listSize;
		}

		List<AdminAllDonationResponseDTO> donationLists = adminDonationService.getAdminDonationList(listSize, cp,
				userIdx);

		if (donationLists == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "잘못된 접근"));
		} else if (donationLists.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "데이터가 존재하지 않음"));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkResponseDTO<List<AdminAllDonationResponseDTO>>(200, "게시판 조회 성공", donationLists));
		}

	}

	/**
	 * (공통) 로그인 및 괸리자 검증 메서드
	 * 
	 * @param session 로그인 검증 세션
	 * 
	 * @return 관리자 계정으로 로그인 확인
	 */
	public LoginResponseDTO shelterUserCheck(HttpSession session) {
		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");

		if (loginUser == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인 후 이용가능");
		}
		if (loginUser.getUserTypeIdx() != 3) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "보호소 사용자만 접근 가능");
		}

		return loginUser;
	}

}
