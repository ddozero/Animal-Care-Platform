package com.animal.api.support.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
 * @since 2025-06-19
 * @see com.animal.api.support.model.response.UserNoticeResponseDTO;
 */
@RestController
@RequestMapping("/api/support")
public class UserSupportController {

	@Autowired
	private UserSupportService supportService;

	/**
	 * @param 현재 페이지 번호
	 * @return 사용자에게 보여줄 고객지원 페이지의 공지사항 목록
	 * @param 사용자가 검색시 입력한 제목
	 * @param 사용자가 검색시 입력한 내용
	 * @return 사용자에게 보여줄 키워드 검색 목록 조회
	 */
	@GetMapping()
	public ResponseEntity<?> getAllNotice(@RequestParam(value = "cp", defaultValue = "0") int cp,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "content", required = false) String content) {

		int listSize = 5;
		if (cp == 0) {
			cp = 1;
		} else {
			cp = (cp - 1) * listSize;
		}

		List<UserNoticeResponseDTO> noticeAllList = null;

		if (title != null || content != null) {
			noticeAllList = supportService.searchAllNotice(listSize, cp, title, content);
		} else {
			noticeAllList = supportService.getAllNotice(listSize, cp);
		}

		if (noticeAllList == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "잘못된 접근"));
		} else if (noticeAllList.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "데이터 없음"));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkResponseDTO<List<UserNoticeResponseDTO>>(200, "조회 성공", noticeAllList));
		}
	}

	@GetMapping("/{idx}")
	public ResponseEntity<?> getNoticeDetail(@PathVariable int idx) {
		UserNoticeResponseDTO dto = supportService.getNoticeDetail(idx);

		if (dto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "삭제되거나 없는 게시물"));
		} else {
			return ResponseEntity.ok(new OkResponseDTO<UserNoticeResponseDTO>(200, "게시물 상세정보 조회 성공", dto));
		}
	}

}
