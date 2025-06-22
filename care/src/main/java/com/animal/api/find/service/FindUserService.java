package com.animal.api.find.service;

import com.animal.api.find.model.request.FindUserPasswordResetRequestDTO;
import com.animal.api.find.model.request.FindUserPasswordVerifyRequestDTO;
import com.animal.api.find.model.response.FindUserIdResponseDTO;

public interface FindUserService {
	// ID 찾기
	FindUserIdResponseDTO findUserId(String name, String email);
	
	// 비밀번호 찾기 아이디 검색
	public void initUserPasswordReset(String userid);

	// 비밀번호 초기화 전 인증
	void verifyUserPasswordResetCode(FindUserPasswordVerifyRequestDTO dto);

	// 비밀번호 재설정
	void resetUserPassword(FindUserPasswordResetRequestDTO dto);
}
