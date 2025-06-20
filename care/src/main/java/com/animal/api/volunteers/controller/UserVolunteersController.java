package com.animal.api.volunteers.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.common.model.ErrorResponseDTO;
import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.volunteers.model.response.VolunteersListResponseDTO;
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
	 * 
	 * @param 현재 페이지 번호
	 * @return 사용자 봉사 페이지에서 보여줄 봉사 목록
	 */
	@GetMapping
	public ResponseEntity<?> getVolunteersList(@RequestParam(value="cp", defaultValue = "0")int cp){
		
		int listSize = 5;
		if(cp==0) {
			cp = 1;
		}else {
			cp = (cp-1) * listSize;
		}
		
		List<VolunteersListResponseDTO> volunteersAllList = volunteerService.getAllVolunteers(listSize, cp);
		
		if(volunteersAllList == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400,"잘못된 접근"));
		} else if(volunteersAllList.size()==0){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "데이터 없음"));
		}else {
			return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<List<VolunteersListResponseDTO>>(200,"게시물 목록 조회 성공",volunteersAllList));
		}
		
	}

}
