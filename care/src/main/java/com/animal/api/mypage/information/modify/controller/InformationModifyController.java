package com.animal.api.mypage.information.modify.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.auth.model.response.LoginResponseDTO;
import com.animal.api.common.aop.util.SessionUtils;
import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.mypage.information.modify.model.response.InformationModifyResponseDTO;
import com.animal.api.mypage.information.modify.service.InformationModifyService;

import lombok.RequiredArgsConstructor;

/**
 * 내 정보 수정 컨트롤러
 * @author Whistler95
 * @since 2025-06-23
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage/information")
public class InformationModifyController {

	private final InformationModifyService informationModifyService;
	
	/**
	 * 내 정보 수정폼
	 * @param request
	 * @return userInfo 로그인 한 유저의 내 정보 목록
	 */
	@GetMapping("/modify")
	public ResponseEntity<?> getUserInfo(HttpServletRequest request){
		LoginResponseDTO loginUser = SessionUtils.getLoginUser(request);
		if(loginUser == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new OkResponseDTO<>(401, "로그인 정보가 없습니다", null));
		}
		
		int userIdx = loginUser.getIdx();
		InformationModifyResponseDTO userInfo = informationModifyService.getUserInformation(userIdx);
		return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<InformationModifyResponseDTO>(200, "내 정보 불러오기 성공", userInfo));
	}
}
