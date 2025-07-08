package com.animal.api.mypage.board.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.auth.model.response.LoginResponseDTO;
import com.animal.api.common.aop.util.SessionUtils;
import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.mypage.board.service.BoardService;

/**
 * 마이페이지 내가 쓴 글 컨트롤러
 * 
 * @author sdr02
 * @since 2025-06-25
 */
@RestController
@RequestMapping("/api/mypage/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	/**
	 * 내가 작성한 게시글 조회 API 
	 * 자유게시판(2), 보호소게시판(3)만 조회 대상이며 공지사항(1)은 제외됨 
	 * 최대 10개씩 페이징 처리하며 전체 개수도 함께 제공됨
	 * 
	 * @param request 로그인 사용자 세션 정보
	 * @param page    조회할 페이지 번호 (기본값: 1)
	 * @return 내가 작성한 게시글 목록 (총 개수 + 리스트)
	 */
	@GetMapping("/written")
	public ResponseEntity<?> getMyWrittenBoards(HttpServletRequest request,
			@RequestParam(defaultValue = "1") int page) {
		LoginResponseDTO loginUser = SessionUtils.getLoginUser(request);

		if (loginUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new OkResponseDTO<>(401, "로그인 정보가 없습니다", null));
		}

		return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<>(200, "내가 쓴 게시글 조회 성공",
				boardService.getWrittenBoardListByUserIdx(loginUser.getIdx(), page)));
	}

	/**
	 * 내가 좋아요한 게시글 조회 API 
	 * 모든 게시판(공지, 자유, 보호소 포함)에서 좋아요 누른 게시글만 조회 
	 * 최대 10개씩 페이징 처리하며 전체 개수도 함께 제공됨
	 *
	 * @param request 로그인 사용자 세션 정보
	 * @param page    조회할 페이지 번호 (기본값: 1)
	 * @return 좋아요한 게시글 목록 (총 개수 + 리스트)
	 */
	@GetMapping("/liked")
	public ResponseEntity<?> getMyLikedBoards(HttpServletRequest request, @RequestParam(defaultValue = "1") int page) {
		LoginResponseDTO loginUser = SessionUtils.getLoginUser(request);

		if (loginUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new OkResponseDTO<>(401, "로그인 정보가 없습니다", null));
		}

		return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<>(200, "좋아요한 게시글 조회 성공",
				boardService.getLikedBoardListByUserIdx(loginUser.getIdx(), page)));
	}
	
	@GetMapping("/activity")
	public ResponseEntity<?> getActivity(
	    HttpServletRequest request,
	    @RequestParam(defaultValue = "1") int pageWritten,
	    @RequestParam(defaultValue = "1") int pageLiked
	) {
	    LoginResponseDTO loginUser = SessionUtils.getLoginUser(request);
	    if (loginUser == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	            .body(new OkResponseDTO<>(401, "로그인 정보가 없습니다", null));
	    }

	    Map<String, Object> result = new HashMap<>();
	    result.put("written", boardService.getWrittenBoardListByUserIdx(loginUser.getIdx(), pageWritten));
	    result.put("liked", boardService.getLikedBoardListByUserIdx(loginUser.getIdx(), pageLiked));

	    return ResponseEntity.ok(new OkResponseDTO<>(200, "활동 내역 조회 성공", result));
	}
}
