package com.animal.api.management.dashboard.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.auth.model.response.LoginResponseDTO;
import com.animal.api.common.model.ErrorResponseDTO;
import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.management.dashboard.model.response.SheltervolunteerDashboardResponseDTO;
import com.animal.api.management.dashboard.service.ShelterDashboardService;

@RestController
@RequestMapping("/api/management/dashboards")
public class ShelterDashboardController {

	@Autowired
	private ShelterDashboardService service;

	@GetMapping("/volunteers")
	public ResponseEntity<?> getVolunteerDashboard(HttpSession session) {
		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");

		if (loginUser == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginUser.getUserTypeIdx() != 2) { // 보호시설 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "보호시설 회원만 접근 가능합니다."));
		}

		List<SheltervolunteerDashboardResponseDTO> dashboardList = service.getVolunteerDashboard(loginUser.getIdx());

		if (dashboardList == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "잘못된 요청입니다"));
		} else if (dashboardList.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "조회된 데이터가 없습니다."));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkResponseDTO<List<SheltervolunteerDashboardResponseDTO>>(200, "조회 성공", dashboardList));
		}
	}
}
