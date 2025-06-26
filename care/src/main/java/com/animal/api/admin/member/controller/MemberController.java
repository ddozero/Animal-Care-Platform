package com.animal.api.admin.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.admin.member.model.response.MemberListResponseDTO;
import com.animal.api.admin.member.service.MemberService;
import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.common.model.PageResult;

/**
 * [관리자 전용] 회원관리 컨트롤러
 * 
 * @author Whistler95
 * @since 2025-06-26
 */
@RestController
@RequestMapping("/api/admin/member")
public class MemberController {

	@Autowired
	MemberService memberService;
	
	/**
	 * [관리자 전용] 회원 목록 조회 컨트롤러
	 * 
	 * @param keyword 검색어 (아이디, 닉네임, 이메일 중 하나)
	 * @param type 검색타입 ("id", "nickname", "email")
	 * @param userType 회원 유형 필터 (일반 사용자, 보호시설)
	 * @param status 회원 상태 필터 (회원, 정지, 탈퇴)
	 * @param page 페이지 번호 (기본 값: 1) 
	 * @param size 페이지당 항목 수 (기본 값: 10)
	 * @return 페이지당 회원 목록 
	 */
	public ResponseEntity<OkResponseDTO<PageResult<MemberListResponseDTO>>> getMemberList(
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String type,
			@RequestParam(required = false) String userType,
			@RequestParam(required = false) Integer status,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size){
		
		PageResult<MemberListResponseDTO> result = memberService.getMemberList(keyword, userType, status, status, page, size);
		return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<PageResult<MemberListResponseDTO>>(200, "회원 목록 조회 성공", result));
	}
}
