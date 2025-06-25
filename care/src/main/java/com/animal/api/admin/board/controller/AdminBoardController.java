package com.animal.api.admin.board.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.admin.board.model.request.NoticeInsertRequestDTO;
import com.animal.api.admin.board.model.request.NoticeUpdateRequestDTO;
import com.animal.api.admin.board.service.AdminBoardService;
import com.animal.api.auth.model.response.LoginResponseDTO;
import com.animal.api.common.model.ErrorResponseDTO;
import com.animal.api.common.model.OkResponseDTO;

/**
 * 사이트 관리자 페이지의 게시글 관련 기능 클래스
 * 
 * @author Rege-97
 * @since 2025-06-25
 * @see com.animal.api.admin.board.model.request.NoticeUpdateRequestDTO
 * @see com.animal.api.admin.board.model.request.NoticeInsertRequestDTO
 */
@RestController
@RequestMapping("/api/admin/boards")
public class AdminBoardController {

	@Autowired
	private AdminBoardService service;

	/**
	 * 관리자 페이지에서 공지사항을 수정하는 메서드
	 * 
	 * @param idx     게시글 번호
	 * @param dto     수정될 입력 폼 데이터
	 * @param session 로그인 검증을 위한 세션
	 * @return 성공 또는 실패 메세지
	 */
	@PutMapping("/notices/{idx}")
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
		} else if (result == service.NOTICE_NOT_FOUND) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "해당 글을 찾을 수 없습니다."));
		} else if (result == service.NOT_NOTICE) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "해당 글은 공지사항이 아닙니다."));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "공지사항 수정 실패"));
		}
	}

	/**
	 * 관리자 페이지에서 공지사항을 삭제하는 메서드
	 * 
	 * @param idx     게시글 번호
	 * @param session 로그인 검증을 위한 세션
	 * @return 성공 또는 실패 메세지
	 */
	@DeleteMapping("/notices/{idx}")
	public ResponseEntity<?> deleteNotice(@PathVariable int idx, HttpSession session) {
		LoginResponseDTO loginAdmin = (LoginResponseDTO) session.getAttribute("loginAdmin");

		if (loginAdmin == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginAdmin.getUserTypeIdx() != 3) { // 관리자 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "관리자만 접근 가능합니다."));
		}

		int result = service.deleteNotice(idx);

		if (result == service.DELETE_SUCCESS) {
			return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<Void>(200, "공지사항 삭제 성공", null));
		} else if (result == service.NOTICE_NOT_FOUND) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "해당 글을 찾을 수 없습니다."));
		} else if (result == service.NOT_NOTICE) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "해당 글은 공지사항이 아닙니다."));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "공지사항 삭제 실패"));
		}
	}

	/**
	 * 관리자 페이지에서 공지사항을 등록하는 메서드
	 * @param dto 공지사항 입력 폼 데이터
	 * @param session 로그인 검증을 위한 세션
	 * @return 성공 또는 실패 메세지
	 */
	@PostMapping("/notices")
	public ResponseEntity<?> insertNotice(@Valid @RequestBody NoticeInsertRequestDTO dto, HttpSession session) {
		LoginResponseDTO loginAdmin = (LoginResponseDTO) session.getAttribute("loginAdmin");

		if (loginAdmin == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginAdmin.getUserTypeIdx() != 3) { // 관리자 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "관리자만 접근 가능합니다."));
		}

		int result = service.insertNotice(dto, loginAdmin.getIdx());

		if (result == service.POST_SUCCESS) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new OkResponseDTO<Void>(201, "공지사항 등록 성공", null));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "공지사항 등록 실패"));
		}
	}
	
}
