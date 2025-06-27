package com.animal.api.admin.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.admin.member.model.request.MemberUpdateRequestDTO;
import com.animal.api.admin.member.model.response.MemberDatailResopnseDTO;
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
	 * [관리자 전용] 회원 목록 조회 API
	 * 
	 * 검색 조건과 페이징 정보를 바탕으로 전체 회원(일반 + 보호시설) 목록을 조회합니다
	 * 
	 * @param keyword 검색어 (아이디, 닉네임, 이메일 중 하나)
	 * @param type 검색타입 ("id", "nickname", "email")
	 * @param userType 회원 유형 필터 (일반 사용자, 보호시설)
	 * @param status 회원 상태 필터 (회원, 정지, 탈퇴)
	 * @param page 페이지 번호 (기본 값: 1) 
	 * @param size 페이지당 항목 수 (기본 값: 10)
	 * @return 페이지당 회원 목록 
	 */
	@GetMapping
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
	
	/**
	 * [관리자 전용] 회원 상세 정보 조회 API
	 *
	 * 특정 회원의 기본 정보 및 보호시설 정보를 상세 조회합니다
	 * 보호소 회원일 경우 shelter 정보가 함께 반환됩니다
	 *
	 * @param userIdx 회원 PK (USER_IDX)
	 * @return 회원 상세 정보
	 */
	@GetMapping("/{userIdx}")
	public ResponseEntity<OkResponseDTO<MemberDatailResopnseDTO>> getUserDetail(@PathVariable int userIdx) {
		MemberDatailResopnseDTO dto = memberService.getMemberDetail(userIdx);
	    return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<>(200, "회원 상세 조회 성공", dto));
	}
	
	/**
	 * [관리자 전용] 회원 정보 수정 API
	 *
	 * 회원의 기본 정보 및 보호소 정보를 수정합니다.
	 * 보호소 회원일 경우 shelter 정보도 함께 수정됩니다
	 *
	 * @param dto 수정 대상 회원 정보
	 * @return 성공 메시지
	 */
	@PutMapping
	public ResponseEntity<OkResponseDTO<String>> updateMember(@RequestBody MemberUpdateRequestDTO dto) {
	    memberService.updateMember(dto);
	    return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<>(200, "회원 정보 수정 완료", null));
	}
	
	/**
	 * [관리자 전용] 회원 탈퇴 처리 API
	 *
	 * 회원의 상태(STATUS)를 -1로 변경하여 탈퇴 처리합니다
	 *
	 * @param userIdx 탈퇴할 회원의 PK
	 * @return 성공 메시지
	 */
	@DeleteMapping("/{userIdx}")
	public ResponseEntity<OkResponseDTO<String>> deleteMember(@PathVariable int userIdx) {
	    memberService.deleteMember(userIdx);
	    return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<>(200, "회원 탈퇴 처리 완료", null));
	}
	
}
