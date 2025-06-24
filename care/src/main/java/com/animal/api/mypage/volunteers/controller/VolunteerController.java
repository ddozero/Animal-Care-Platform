package com.animal.api.mypage.volunteers.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.auth.model.response.LoginResponseDTO;
import com.animal.api.common.aop.util.SessionUtils;
import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.mypage.volunteers.model.response.VolunteerListResponseDTO;
import com.animal.api.mypage.volunteers.service.VolunteerService;

@RestController
@RequestMapping("/api/mypage")
public class VolunteerController {
	@Autowired
	private VolunteerService volunteerService;

	@GetMapping("/volunteers")
	public ResponseEntity<?> getMyVolunteerHistory(HttpServletRequest request) {

		LoginResponseDTO loginUser = SessionUtils.getLoginUser(request);

		if (loginUser == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new OkResponseDTO<>(401, "로그인 정보가 없습니다", null));
		}
		
		List<VolunteerListResponseDTO> list = volunteerService.getVolunteerListByUserIdx(loginUser.getIdx());

		return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<List<VolunteerListResponseDTO>>(200,"내 봉사 내역 조회 성공", list));
	}

}
