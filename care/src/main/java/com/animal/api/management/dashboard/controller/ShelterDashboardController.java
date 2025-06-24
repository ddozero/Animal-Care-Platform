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
import com.animal.api.management.dashboard.model.response.ShelterAnimalDashboardResponseDTO;
import com.animal.api.management.dashboard.model.response.ShelterViewDashboardResponseDTO;
import com.animal.api.management.dashboard.model.response.ShelterVolunteerDashboardResponseDTO;
import com.animal.api.management.dashboard.service.ShelterDashboardService;

/**
 * 
 * @author Rege-97
 * @since 2025-06-24
 * @see com.animal.api.management.dashboard.model.response.ShelterAnimalDashboardResponseDTO
 * @see com.animal.api.management.dashboard.model.response.ShelterViewDashboardResponseDTO
 * @see com.animal.api.management.dashboard.model.response.ShelterVolunteerDashboardResponseDTO
 */
@RestController
@RequestMapping("/api/management/dashboards")
public class ShelterDashboardController {

	@Autowired
	private ShelterDashboardService service;

	/**
	 * 보호시설 관리페이지에서 봉사완료에 대한 통계 조회 메서드
	 * 
	 * @param session 로그인 검증을 위한 세션
	 * @return 최근 6개월의 봉사 통계데이터
	 */
	@GetMapping("/volunteers")
	public ResponseEntity<?> getVolunteerDashboard(HttpSession session) {
		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");

		if (loginUser == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginUser.getUserTypeIdx() != 2) { // 보호시설 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "보호시설 회원만 접근 가능합니다."));
		}

		List<ShelterVolunteerDashboardResponseDTO> dashboardList = service.getVolunteerDashboard(loginUser.getIdx());

		if (dashboardList == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "잘못된 요청입니다"));
		} else if (dashboardList.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "조회된 데이터가 없습니다."));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkResponseDTO<List<ShelterVolunteerDashboardResponseDTO>>(200, "조회 성공", dashboardList));
		}
	}

	/**
	 * 보호시설 관리페이지에서 유기동물에 대한 통계 조회 메서드
	 * 
	 * @param session 로그인 검증을 위한 세션
	 * @return 올해의 유기동물 통계데이터
	 */
	@GetMapping("/animals")
	public ResponseEntity<?> getAnimalDashboard(HttpSession session) {
		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");

		if (loginUser == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginUser.getUserTypeIdx() != 2) { // 보호시설 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "보호시설 회원만 접근 가능합니다."));
		}

		ShelterAnimalDashboardResponseDTO dto = service.getAdoptionDashboard(loginUser.getIdx());

		if (dto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "조회된 데이터가 없습니다"));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkResponseDTO<ShelterAnimalDashboardResponseDTO>(200, "조회 성공", dto));
		}
	}

	/**
	 * 보호시설 관리페이지에서 게시글 조회수에 대한 통계 조회 메서드
	 * 
	 * @param session 로그인 검증을 위한 세션
	 * @return 최근 6개월의 조회수 통계데이터
	 */
	@GetMapping("/views")
	public ResponseEntity<?> getViewDashboard(HttpSession session) {
		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");

		if (loginUser == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginUser.getUserTypeIdx() != 2) { // 보호시설 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "보호시설 회원만 접근 가능합니다."));
		}

		List<ShelterViewDashboardResponseDTO> dashboardList = service.getViewDashboard(loginUser.getIdx());

		if (dashboardList == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "잘못된 요청입니다"));
		} else if (dashboardList.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "조회된 데이터가 없습니다."));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkResponseDTO<List<ShelterViewDashboardResponseDTO>>(200, "조회 성공", dashboardList));
		}
	}
}
