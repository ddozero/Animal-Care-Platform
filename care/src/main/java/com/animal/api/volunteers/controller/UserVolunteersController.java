package com.animal.api.volunteers.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.auth.model.response.LoginResponseDTO;
import com.animal.api.common.model.ErrorResponseDTO;
import com.animal.api.common.model.OkPageResponseDTO;
import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.common.model.PageInformationDTO;
import com.animal.api.volunteers.model.request.VolunteersSubmitRequestDTO;
import com.animal.api.volunteers.model.response.AllVolunteersResponseDTO;
import com.animal.api.volunteers.service.UserVolunteersService;

/**
 * 사용자의 고객지원 페이지 공지사항 관련 컨트롤러 클래스
 * 
 * @author doyeong
 * @since 2025-06-20
 * @see com.animal.api.support.model.response.VolunteersListResponseDTO;
 * @see com.animal.api.support.model.response.AllVolunteersResponseDTO;
 * @see com.animal.api.support.model.response.VolunteersSubmitRequestDTO
 */
@RestController
@RequestMapping("/api/volunteers")
public class UserVolunteersController {

	@Autowired
	private UserVolunteersService volunteerService;

	/**
	 * 사용자의 봉사 목록 조회 메서드
	 * 
	 * @param title         봉사 제목
	 * @param content       봉사 내용
	 * @param cp            현재 페이지 번호
	 * @param location      봉사 장소
	 * @param status        봉사 모집 상태
	 * @param shelter       봉사 보호소 이름
	 * @param shelterType   봉사 보호소 기관 종류
	 * @param volunteerDate 봉사일
	 * @param type          봉사 타입
	 * @param time          봉사 활동 및 인정 시간
	 * 
	 * @return 사용자의 봉사페이지에서 보여줄 봉사 목록(조건 검색시 포함)
	 */
	@GetMapping
	public ResponseEntity<?> getVolunteersList(@RequestParam(value = "cp", defaultValue = "0") int cp,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "content", required = false) String content,
			@RequestParam(value = "location", required = false) String location,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "shelter", required = false) String shelter,
			@RequestParam(value = "shelterType", required = false) String shelterType,
			@RequestParam(value = "volunteerDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate volunteerDate,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "time", required = false) String timeStr) {
		
		//time 5이상 검색 조건 추가
		int time = 0;
		if ("6plus".equals(timeStr)) {
			time = 9999;
		} else if (timeStr != null && !timeStr.isEmpty()) {
			try {
				time = Integer.parseInt(timeStr);
			} catch (NumberFormatException e) {
				time = 0; // 잘못된 값이면 0 (전체)
			}
		}

		List<AllVolunteersResponseDTO> volunteersAllList = null;
		PageInformationDTO pageInfo = null;

		if (title != null || content != null || location != null || status != null || shelter != null
				|| shelterType != null || volunteerDate != null || type != null || timeStr != null) {
			volunteersAllList = volunteerService.searchVolunteers(cp, title, content, location, status, shelter,
					shelterType, volunteerDate, type, time);
			pageInfo = volunteerService.getSearchVolunteersPage(cp, title, content, location, status, shelter,
					shelterType, volunteerDate, type, time);
		} else {
			volunteersAllList = volunteerService.getAllVolunteers(cp);
			pageInfo = volunteerService.getAllVolunteersPage(cp);
		}

		if (volunteersAllList == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorResponseDTO(400, "잘못된 접근입니다. 관리자에게 문의하세요."));
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(new OkPageResponseDTO<List<AllVolunteersResponseDTO>>(200,
					"게시물 목록 조회 성공", volunteersAllList, pageInfo));
		}
	}

	/**
	 * 봉사페이지의 목록을 캘린더 일정으로 조회하는 메서드
	 * 
	 * @return 캘린더 조회 성공 여부
	 */
	@GetMapping("/calendar")
	public ResponseEntity<?> getVolunteerCalendar() {

		List<AllVolunteersResponseDTO> volunteersCal = volunteerService.getVolunteerCalendar();

		if (volunteersCal == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorResponseDTO(400, "잘못된 접근입니다. 관리자에게 문의하세요."));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkResponseDTO<List<AllVolunteersResponseDTO>>(200, "게시물 목록 조회 성공", volunteersCal));
		}
	}

	/**
	 * 봉사페이지의 상세 정보를 조회하는 메서드
	 * 
	 * @param idx 봉사 관리 번호
	 * @return 사용자 봉사 페이지에서 보여줄 봉사 정보
	 */

	@GetMapping("/{idx}")
	public ResponseEntity<?> getVolunteersDetail(@PathVariable int idx) {

		AllVolunteersResponseDTO dto = volunteerService.getVolunteersDetail(idx);

		if (dto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "봉사활동이 존재하지 않습니다."));
		} else {
			return ResponseEntity.ok(new OkResponseDTO<AllVolunteersResponseDTO>(200, "봉사 상세정보 조회 성공", dto));
		}
	}

	/**
	 * 봉사페이지의 로그인한 사용자의 봉사신청 메서드
	 * 
	 * @param dto     봉사 신청 폼 목록
	 * @param idx     봉사 관리 번호
	 * @param session 로그인 검증 세션
	 * @return 신청에 따른 메세지
	 */
	@PostMapping("/{idx}/submit")
	public ResponseEntity<?> submitVolunteers(@Valid @RequestBody VolunteersSubmitRequestDTO dto,
			@PathVariable("idx") int idx, HttpSession session) {
		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");

		if (loginUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용가능합니다."));
		}
		dto.setUserIdx(loginUser.getIdx());

		dto.setVolunteerIdx(idx);

		int result = volunteerService.submitVolunteers(dto);

		if (result == volunteerService.SUBMIT_OK) {
			return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<Void>(200, "봉사 신청이 완료되었습니다.", null));
		} else if (result == volunteerService.SUBMIT_DUPLICATE) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseDTO(409, "이미 신청이 완료된 봉사입니다."));
		} else if (result == volunteerService.SUBMIT_NOT_OK) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseDTO(409, "봉사 신청이 불가능합니다."));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorResponseDTO(400, "잘못된 접근입니다. 관리자에게 문의하세요."));
		}
	}

}
