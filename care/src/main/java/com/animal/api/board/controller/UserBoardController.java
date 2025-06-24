package com.animal.api.board.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.auth.model.response.LoginResponseDTO;
import com.animal.api.board.model.request.BoardWriteRequestDTO;
import com.animal.api.board.model.response.AllBoardListResponseDTO;
import com.animal.api.board.service.UserBoardService;
import com.animal.api.common.model.ErrorResponseDTO;
import com.animal.api.common.model.OkResponseDTO;

/**
 * @author consgary
 * @since 2025.06.24
 * @see com.animal.api.board.model.response.AllBoardListResponseDTO
 * @see com.animal.api.board.model.request.BoardSearchRequestDTO
 * @see com.animal.api.board.model.request.BoardWriteRequestDTO
 */
@RestController
@RequestMapping("/api/boards")
public class UserBoardController {

	@Autowired
	private UserBoardService service;

	/**
	 * 전체 게시판 검색 , 조건(제목,닉네임,본문,전체) 검색
	 * 
	 * @param cp      현재 페이지
	 * @param type    검색 조건
	 * @param keyword 검색어
	 * @return 전체 검색 게시판 리스트 or 조건 검색 게시판 리스트
	 */
	@GetMapping
	public ResponseEntity<?> getBoards(@RequestParam(value = "cp", defaultValue = "0") int cp,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "keyword", required = false) String keyword) {
		int listSize = 3;
		List<AllBoardListResponseDTO> boardList = null;
		if (type != null || keyword != null) {
			boardList = service.searchBoards(type, keyword, listSize, cp);
		} else {
			boardList = service.getAllBoards(listSize, cp);

		}
		if (boardList == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "잘못된 요청"));
		} else if (boardList.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "데이터가 존재하지않음"));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkResponseDTO<List<AllBoardListResponseDTO>>(200, "게시판 조회 성공", boardList));
		}
	}

	/**
	 * 게시판 글 등록
	 * 
	 * @param dto     글 등록 폼
	 * @param session 로그인 검증용
	 * @return 글 등록 성공,실패 메세지
	 */
	@PostMapping
	public ResponseEntity<?> addBoard(@Valid @RequestBody BoardWriteRequestDTO dto, HttpSession session) {
		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");

		if (loginUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		int result = service.addBoard(dto);

		if (result == service.POST_SUCCESS) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new OkResponseDTO<Void>(201, "게시판 글 등록 성공", null));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "게시판 글 등록 실패"));
		}
	}

}
