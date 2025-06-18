package com.animal.api.support.controller;

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

	/**
	 * 
	 * @param 현재 페이지 번호
	 * @return 사용자에게 보여줄 고객지원 페이지의 공지사항 목록
	 */
	@GetMapping("/getAllNotice")
	public ResponseEntity<?> getAllNotice(@RequestParam(value = "cp", defaultValue = "0") int cp) {
		int listSize = 5;

		if (cp == 1) {
			cp = 0;
		} else {
			cp = (cp - 1) * listSize;
		}

		List<UserNoticeResponseDTO> lists = supportService.getAllNotice(listSize, cp);

		if (lists.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "등록 게시물 없음"));
		}

		return ResponseEntity.ok(new OkResponseDTO<List<UserNoticeResponseDTO>>(200, "게시물 조회 성공", lists));

	}
}
