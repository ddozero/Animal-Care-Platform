package com.animal.api.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.board.model.response.AllBoardListResponseDTO;
import com.animal.api.board.service.UserBoardService;
import com.animal.api.common.model.ErrorResponseDTO;
import com.animal.api.common.model.OkResponseDTO;

/**
 * @author consgary
 * @since 2025.06.23
 * @see com.animal.api.board.model.response.AllBoardListResponseDTO
 */
@RestController
@RequestMapping("/api/boards")
public class UserBoardController {

	@Autowired
	private UserBoardService service;

	/**
	 * 자유게시판 전체 조회
	 * 
	 * @param cp 현재 페이지
	 * @return 게시판 전체 리스트
	 */
	@GetMapping
	public ResponseEntity<?> getBoards(@RequestParam(value = "cp", defaultValue = "0") int cp) {
		int listSize = 3;
		List<AllBoardListResponseDTO> boardList = service.getAllBoards(listSize, cp);

		if (boardList == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "잘못된 요청"));
		} else if (boardList.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "데이터가 존재하지않음"));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkResponseDTO<List<AllBoardListResponseDTO>>(200, "게시판 전체 조회 성공", boardList));
		}
	}
}
