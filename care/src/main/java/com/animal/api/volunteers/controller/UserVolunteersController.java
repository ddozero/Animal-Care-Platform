package com.animal.api.volunteers.controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.common.model.ErrorResponseDTO;
import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.volunteers.model.response.AllVolunteersResponseDTO;
import com.animal.api.volunteers.service.UserVolunteersService;

/**
 * 사용자의 고객지원 페이지 공지사항 관련 컨트롤러 클래스
 * 
 * @author doyeong
 * @since 2025-06-20
 * @see com.animal.api.support.model.response.VolunteersListResponseDTO;
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
			@RequestParam(value = "volunteerDate", required = false) Timestamp volunteerDate,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "time", defaultValue = "0") int time) {

		int listSize = 5;
		if (cp == 0) {
			cp = 1;
		} else {
			cp = (cp - 1) * listSize;
		}

		List<AllVolunteersResponseDTO> volunteersAllList = null;
		if (title != null || content != null || location != null || status != null || shelter != null
				|| shelterType != null || volunteerDate != null || type != null || time != 0) {
			volunteersAllList = volunteerService.searchVolunteers(listSize, cp, title, content, location, status,
					shelter, shelterType, volunteerDate, type, time);
		} else {
			volunteersAllList = volunteerService.getAllVolunteers(listSize, cp);
		}

		if (volunteersAllList == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "잘못된 접근"));
		} else if (volunteersAllList.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "데이터 없음"));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkResponseDTO<List<AllVolunteersResponseDTO>>(200, "게시물 목록 조회 성공", volunteersAllList));
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
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "삭제되거나 없는 데이터"));
		} else {
			return ResponseEntity.ok(new OkResponseDTO<AllVolunteersResponseDTO>(200, "봉사 상세정보 조회 성공", dto));
		}
	}

}
