package com.animal.api.support.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.common.model.ErrorResponseDTO;
import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.support.model.response.UserNoticeResponseDTO;
import com.animal.api.support.service.UserSupportService;

/**
 * 사용자의 고객지원 페이지 공지사항 관련 컨트롤러 클래스
 * 
 * @author ddozero
 * @since 2025-06-18
 * @see com.animal.api.support.model.response.UserNoticeResponseDTO;
 */
@RestController
@RequestMapping("/api/support")
public class UserSupportController {

	@Autowired
	private UserSupportService supportService;

	@GetMapping("/getAllNotice")
	public ResponseEntity<?> getAllNotice() {
		try {
			List<UserNoticeResponseDTO> lists = supportService.getAllNotice();

			if (lists != null) {
				if (lists.isEmpty()) {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "등록 게시물 없음"));
				} else {
					return ResponseEntity.ok(new OkResponseDTO<List<UserNoticeResponseDTO>>(200, "게시물 조회 성공", lists));
				}
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(500, "서버 오류"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(500, "서버 내부 오류 발생"));
		}
	}
	
	
	
	
}
