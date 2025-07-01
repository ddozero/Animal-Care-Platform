package com.animal.api.admin.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.animal.api.admin.board.model.request.NoticeInsertRequestDTO;
import com.animal.api.admin.board.model.request.NoticeUpdateRequestDTO;
import com.animal.api.admin.board.service.AdminBoardService;
import com.animal.api.auth.model.response.LoginResponseDTO;
import com.animal.api.board.model.response.AllBoardListResponseDTO;
import com.animal.api.board.model.response.BoardDetailResponseDTO;
import com.animal.api.board.service.UserBoardService;
import com.animal.api.common.model.ErrorResponseDTO;
import com.animal.api.common.model.OkPageResponseDTO;
import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.common.model.PageInformationDTO;
import com.animal.api.support.model.response.UserNoticeResponseDTO;
import com.animal.api.support.service.UserSupportService;

/**
 * 사이트 관리자 페이지의 게시글 관련 기능 클래스
 * 
 * @author Rege-97
 * @since 2025-06-27
 * @see com.animal.api.admin.board.model.request.NoticeUpdateRequestDTO
 * @see com.animal.api.admin.board.model.request.NoticeInsertRequestDTO
 * @see com.animal.api.board.model.response.AllBoardListResponseDTO
 * @see com.animal.api.board.model.response.BoardDetailResponseDTO
 * @see com.animal.api.support.model.response.UserNoticeResponseDTO
 * @see com.animal.api.board.model.response.BoardDetailResponseDTO
 */
@RestController
@RequestMapping("/api/admin/boards")
public class AdminBoardController {

	@Autowired
	private AdminBoardService adminBoardService;
	@Autowired
	private UserBoardService userBoardService;
	@Autowired
	private UserSupportService userSupportService;

	/**
	 * 사이트 관리 페이지의 게시판 리스트 조회
	 * 
	 * @param cp      현재 페이지
	 * @param type    검색조건
	 * @param keyword 검색어
	 * @param session 로그인 검증을 위한 세션
	 * @return 조회된 게시글 리스트
	 */
	@GetMapping
	public ResponseEntity<?> getBoardList(@RequestParam(value = "cp", defaultValue = "0") int cp,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "keyword", required = false) String keyword, HttpSession session) {
		LoginResponseDTO loginAdmin = (LoginResponseDTO) session.getAttribute("loginAdmin");

		if (loginAdmin == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginAdmin.getUserTypeIdx() != 3) { // 관리자 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "관리자만 접근 가능합니다."));
		}
		List<AllBoardListResponseDTO> boardList = null;
		PageInformationDTO pageInfo = null;
		if (type != null || keyword != null) {
			boardList = userBoardService.searchBoards(type, keyword, cp);
			pageInfo = userBoardService.searchBoardsPageInfo(type, keyword, cp);
		} else {
			boardList = userBoardService.getAllBoards(cp);
			pageInfo = userBoardService.getAllBoardsPageInfo(cp);
		}
		if (boardList == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "잘못된 요청"));
		} else if (boardList.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "데이터가 존재하지않음"));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkPageResponseDTO<List<AllBoardListResponseDTO>>(200, "게시판 조회 성공", boardList, pageInfo));
		}
	}

	/**
	 * 사이트 관리 페이지의 게시글 상세정보
	 * 
	 * @param idx     게시글 번호
	 * @param session 로그인 검증을 위한 세션
	 * @return 해당 게시글의 상세정보
	 */
	@GetMapping("/{idx}")
	public ResponseEntity<?> getBoardDetail(@PathVariable int idx, HttpSession session) {
		LoginResponseDTO loginAdmin = (LoginResponseDTO) session.getAttribute("loginAdmin");

		if (loginAdmin == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginAdmin.getUserTypeIdx() != 3) { // 관리자 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "관리자만 접근 가능합니다."));
		}

		BoardDetailResponseDTO boardDetail = userBoardService.getBoardDetail(idx);

		if (boardDetail == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "게시판 상세 데이터가 존재하지않음"));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkResponseDTO<BoardDetailResponseDTO>(200, "게시판 상세 조회 성공", boardDetail));
		}
	}

	/**
	 * 사이트 관리 페이지의 게시글 삭제
	 * 
	 * @param idx     게시글 번호
	 * @param session 로그인 검증을 위한 세션
	 * @return 성공 또는 실패 메세지
	 */
	@DeleteMapping("/{idx}")
	public ResponseEntity<?> deleteBoard(@PathVariable int idx, HttpSession session) {
		LoginResponseDTO loginAdmin = (LoginResponseDTO) session.getAttribute("loginAdmin");

		if (loginAdmin == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginAdmin.getUserTypeIdx() != 3) { // 관리자 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "관리자만 접근 가능합니다."));
		}

		int result = adminBoardService.deleteBoard(idx);

		if (result == adminBoardService.DELETE_SUCCESS) {
			return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<Void>(200, "게시글 삭제 성공", null));
		} else if (result == adminBoardService.NOMAL_BOARD_NOT_FOUND) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "해당 글을 찾을 수 없습니다."));
		} else if (result == adminBoardService.NOT_NOMAL_BOARD) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "해당 글은 일반게시글이 아닙니다."));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "게시글 삭제 실패"));
		}
	}

	/**
	 * 사이트 관리 페이지의 공지사항 조회
	 * 
	 * @param cp      현재 페이지
	 * @param title   글 제목
	 * @param content 본문 내용
	 * @param session 로그인 검증을 위한 세션
	 * @return 조회된 공지사항 리스트
	 */
	@GetMapping("/notices")
	public ResponseEntity<?> getNoticeList(@RequestParam(value = "cp", defaultValue = "0") int cp,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "content", required = false) String content, HttpSession session) {
		LoginResponseDTO loginAdmin = (LoginResponseDTO) session.getAttribute("loginAdmin");

		if (loginAdmin == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginAdmin.getUserTypeIdx() != 3) { // 관리자 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "관리자만 접근 가능합니다."));
		}

		int listSize = 5;

		List<UserNoticeResponseDTO> noticeAllList = null;
		PageInformationDTO page = null;

		if (title != null || content != null) {
			noticeAllList = userSupportService.searchAllNotice(cp, title, content);
			page = userSupportService.searchNoticePage(cp, title, content);
		} else {
			noticeAllList = userSupportService.getAllNotice(cp);
			page = userSupportService.getAllNoticePage(cp);
		}

		if (noticeAllList == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "잘못된 접근"));
		} else if (noticeAllList.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "데이터 없음"));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkPageResponseDTO<List<UserNoticeResponseDTO>>(200, "조회 성공", noticeAllList, page));
		}
	}

	/**
	 * 사이트 관리 페이지에서의 공지사항 상세정보
	 * 
	 * @param idx     게시글 번호
	 * @param session 로그인 검증을 위한 세션
	 * @return 조회된 공지사항 상세정보
	 */
	@GetMapping("/notices/{idx}")
	public ResponseEntity<?> getNoticeDetail(@PathVariable int idx, HttpSession session) {
		LoginResponseDTO loginAdmin = (LoginResponseDTO) session.getAttribute("loginAdmin");

		if (loginAdmin == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginAdmin.getUserTypeIdx() != 3) { // 관리자 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "관리자만 접근 가능합니다."));
		}

		int result = userSupportService.addNoticeViewCount(idx);
		UserNoticeResponseDTO dto = userSupportService.getNoticeDetail(idx);

		if (dto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "삭제되거나 없는 게시물"));
		} else {
			return ResponseEntity.ok(new OkResponseDTO<UserNoticeResponseDTO>(200, "게시물 상세정보 조회 성공", dto));
		}
	}

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

		int result = adminBoardService.updateNotice(dto, idx);

		if (result == adminBoardService.UPDATE_SUCCESS) {
			return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<Void>(200, "공지사항 수정 성공", null));
		} else if (result == adminBoardService.NOTICE_NOT_FOUND) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "해당 글을 찾을 수 없습니다."));
		} else if (result == adminBoardService.NOT_NOTICE) {
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

		int result = adminBoardService.deleteNotice(idx);

		if (result == adminBoardService.DELETE_SUCCESS) {
			return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<Void>(200, "공지사항 삭제 성공", null));
		} else if (result == adminBoardService.NOTICE_NOT_FOUND) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "해당 글을 찾을 수 없습니다."));
		} else if (result == adminBoardService.NOT_NOTICE) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "해당 글은 공지사항이 아닙니다."));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "공지사항 삭제 실패"));
		}
	}

	/**
	 * 관리자 페이지에서 공지사항을 등록하는 메서드
	 * 
	 * @param dto     공지사항 입력 폼 데이터
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

		int result = adminBoardService.insertNotice(dto, loginAdmin.getIdx());

		if (result == adminBoardService.POST_SUCCESS) {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("createdIdx", dto.getIdx());
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new OkResponseDTO<Map<String, Integer>>(201, "공지사항 등록 성공", map));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "공지사항 등록 실패"));
		}
	}

	/**
	 * 공지사항 등록 또는 수정 시 첨부파일 업로드 메서드
	 * 
	 * @param idx   게시글 번호
	 * @param files 첨부파일
	 * @return 성공 또는 실패 메세지
	 */
	@PostMapping("/notices/upload/{idx}")
	public ResponseEntity<?> uploadNoticeFiles(@PathVariable int idx, MultipartFile[] files) {
		int result = adminBoardService.uploadNoticeFiles(files, idx);
		if (result == adminBoardService.UPLOAD_SUCCESS) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new OkResponseDTO<Void>(201, "첨부파일 업로드 성공", null));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "첨부파일 업로드 실패"));
		}
	}

}
