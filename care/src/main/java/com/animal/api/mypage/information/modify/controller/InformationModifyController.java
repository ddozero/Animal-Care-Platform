package com.animal.api.mypage.information.modify.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.auth.model.response.LoginResponseDTO;
import com.animal.api.common.aop.util.SessionUtils;
import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.mypage.information.modify.model.request.EmailChangeRequestDTO;
import com.animal.api.mypage.information.modify.model.request.InformationModifyRequsetDTO;
import com.animal.api.mypage.information.modify.model.request.PasswordChangeRequestDTO;
import com.animal.api.mypage.information.modify.model.response.InformationModifyResponseDTO;
import com.animal.api.mypage.information.modify.service.InformationModifyService;

import lombok.RequiredArgsConstructor;

/**
 * 내 정보 수정 컨트롤러
 * 
 * @author Whistler95
 * @since 2025-06-23
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage/information")
public class InformationModifyController {

	private final InformationModifyService informationModifyService;

	/**
	 * 내 정보 수정 조회 및 출력
	 * 
	 * @param request 세션의 정보
	 * @return userInfo 로그인 한 유저의 내 정보 목록
	 */
	@GetMapping("/modify")
	public ResponseEntity<?> getUserInfo(HttpServletRequest request) {

		LoginResponseDTO loginUser = SessionUtils.getLoginUser(request);

		if (loginUser == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new OkResponseDTO<>(401, "로그인 정보가 없습니다", null));
		}

		int userIdx = loginUser.getIdx();
		InformationModifyResponseDTO userInfo = informationModifyService.getUserInformation(userIdx);

		return ResponseEntity.status(HttpStatus.OK)
				.body(new OkResponseDTO<InformationModifyResponseDTO>(200, "내 정보 불러오기 성공", userInfo));
	}

	/**
	 * 내 정보 수정
	 * 
	 * @param InformationModifyRequestDTO 내 정보 수정 목록
	 * @return 정보 수정 완료
	 */
	@PutMapping("/update")
	public ResponseEntity<?> updateUserInfo(@Valid @RequestBody InformationModifyRequsetDTO requestDTO,
			HttpServletRequest request) {

		LoginResponseDTO loginUser = SessionUtils.getLoginUser(request);

		if (loginUser == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new OkResponseDTO<>(401, "로그인 정보가 없습니다", null));
		}

		informationModifyService.updateUserInformation(loginUser.getIdx(), requestDTO);

		return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<>(200, "회원 정보가 성공적으로 수정되었습니다.", null));
	}

	/**
	 * 내 정보 수정 비밀번호 변경
	 * @param requestDTO 비밀번호 변경 입력 폼 값
	 * @param reuqest 세선에 저장된 사용자 정보
	 * @return 비밀번호 변경 완료 / 캡차 인증
	 */
	@PutMapping("/password")
	public ResponseEntity<?> updatePassword(@Valid @RequestBody PasswordChangeRequestDTO requestDTO,
			HttpServletRequest request) {

		LoginResponseDTO loginUser = SessionUtils.getLoginUser(request);

		if (loginUser == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new OkResponseDTO<>(401, "로그인 정보가 없습니다", null));
		}

		informationModifyService.updatePassword(loginUser.getIdx(), requestDTO, request);
		
		return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<>(200, "비밀번호가 성공적으로 변경되었습니다", null));
	}
	
	/**
	 * 내 정보 수정 이메일 변경 
	 * @param requestDTO 이메일 변경 폼 값
	 * @param request 로그인한 유저의 정보
	 * @return 이메일 변경 성공
	 */
	@PutMapping("/email")
	public ResponseEntity<?> updateEmail(@Valid @RequestBody EmailChangeRequestDTO requestDTO, HttpServletRequest request){
		
		LoginResponseDTO loginUser = SessionUtils.getLoginUser(request);

		if (loginUser == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new OkResponseDTO<>(401, "로그인 정보가 없습니다", null));
		}
		
		informationModifyService.updateEmail(loginUser.getIdx(), requestDTO);
		
		return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<>(200, "이메일이 성공적으로 변경되었습니다", null));
	}
}
