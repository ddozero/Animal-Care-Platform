package com.animal.api.board.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.animal.api.auth.model.response.LoginResponseDTO;
import com.animal.api.board.model.request.BoardUpdateRequestDTO;
import com.animal.api.board.model.request.BoardWriteRequestDTO;
import com.animal.api.board.model.response.AllBoardListResponseDTO;
import com.animal.api.board.model.response.BoardDetailResponseDTO;
import com.animal.api.board.service.UserBoardService;
import com.animal.api.common.model.ErrorResponseDTO;
import com.animal.api.common.model.OkResponseDTO;

/**
 * 사용자 기준 자유게시판에 관련되어 있는 컨트롤러 클래스
 * 
 * @author consgary
 * @since 2025.06.24
 * @see com.animal.api.board.model.response.AllBoardListResponseDTO
 * @see com.animal.api.board.model.request.BoardSearchRequestDTO
 * @see com.animal.api.board.model.request.BoardWriteRequestDTO
 * @see com.animal.api.board.model.response.BoardDetailResponseDTO
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
	 * @return 생성된 idx(게시판 테이블 idx)
	 */
	@PostMapping
	public ResponseEntity<?> addBoard(@Valid @RequestBody BoardWriteRequestDTO dto, HttpSession session) {
		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");

		if (loginUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		int result = service.addBoard(dto);

		if (result == service.POST_SUCCESS) {
			Integer boardIdx = dto.getIdx();
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new OkResponseDTO<Integer>(201, "게시판 글 등록 성공", boardIdx));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "게시판 글 등록 실패"));
		}
	}

	/**
	 * 게시판 글 등록시 파일 업로드
	 * 
	 * @param files 프론트에서 받은 파일
	 * @param idx   게시판 글이 등록 되면서 생긴 번호
	 * @return 업로드 성공 또는 실패 메세지
	 */
	@PostMapping("/upload/{idx}")
	public ResponseEntity<?> uploadBoardFile(MultipartFile[] files, @PathVariable int idx) {
		int result = service.uploadBoardFile(files, idx);
		if (result == service.UPLOAD_SUCCESS) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new OkResponseDTO<Void>(201, "파일 업로드 성공", null));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "파일 업로드 실패"));
		}
	}

	/**
	 * 게시판 상세 정보 조회
	 * 
	 * @param idx 게시판 번호
	 * @return 게시판 상세정보
	 */
	@GetMapping("/{idx}")
	public ResponseEntity<?> getBoardDetail(@PathVariable int idx) {

		BoardDetailResponseDTO boardDetail = service.getBoardDetail(idx);

		if (boardDetail == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "게시판 상세 데이터가 존재하지않음"));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkResponseDTO<BoardDetailResponseDTO>(200, "게시판 상세 조회 성공", boardDetail));
		}
	}

	/**
	 * 게시글 수정
	 * 
	 * @param idx     게시판 번호
	 * @param dto     게시글 수정 폼
	 * @param session 로그인 검증용
	 * @return 성공시 메세지와 함께 게시글 idx(파일업로드 활용)/실패시 실패 메세지
	 */
	@PutMapping("/{idx}")
	public ResponseEntity<?> updateBoard(@PathVariable int idx, @Valid @RequestBody BoardUpdateRequestDTO dto,
			HttpSession session) {
		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");
		if (loginUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}
		// 게시글 번호 dto 셋팅
		dto.setIdx(idx);
		// 회원 번호 dto 셋팅
		dto.setUserIdx(loginUser.getIdx());

		int result = service.updateBoard(dto);

		if (result == service.POST_SUCCESS) {
			Integer boardIdx = dto.getIdx();
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new OkResponseDTO<Integer>(200, "게시판 글 수정 성공", boardIdx));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "게시판 글 수정 실패"));
		}
	}
}
