package com.animal.api.admin.board.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.admin.board.model.request.NoticeUpdateRequestDTO;
import com.animal.api.admin.board.service.AdminBoardService;
import com.animal.api.auth.model.response.LoginResponseDTO;
import com.animal.api.common.model.ErrorResponseDTO;
import com.animal.api.common.model.OkResponseDTO;

@RestController
@RequestMapping("/api/admin/boards")
public class AdminBoardController {

	@Autowired
	private AdminBoardService service;

	@PutMapping("/{idx}")
	public ResponseEntity<?> updateNotice(@PathVariable int idx, @Valid @RequestBody NoticeUpdateRequestDTO dto,
			HttpSession session) {
		LoginResponseDTO loginAdmin = (LoginResponseDTO) session.getAttribute("loginAdmin");

		if (loginAdmin == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginAdmin.getUserTypeIdx() != 3) { // 관리자 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "관리자만 접근 가능합니다."));
		}

		int result = service.updateNotice(dto, idx);

		if (result == service.UPDATE_SUCCESS) {
			return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<Void>(200, "공지사항 수정 성공", null));
		} else if(result == service.NOT_NOTICE){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "해당 공지사항이 없거나 해당 글은 공지사항이 아닙니다."));
		}else{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "공지사항 수정 실패"));
		}

	}
}
