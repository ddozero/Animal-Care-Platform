package com.animal.api.mypage.information.modify.service;

import javax.servlet.http.HttpServletRequest;

import com.animal.api.mypage.information.modify.model.request.EmailChangeRequestDTO;
import com.animal.api.mypage.information.modify.model.request.InformationModifyRequsetDTO;
import com.animal.api.mypage.information.modify.model.request.PasswordChangeRequestDTO;
import com.animal.api.mypage.information.modify.model.response.InformationModifyResponseDTO;

public interface InformationModifyService {
	// 내 정보 출력
	InformationModifyResponseDTO getUserInformation(int userIdx);

	// 내 정보 수정
	void updateUserInformation(int userIdx, InformationModifyRequsetDTO dto);
	
	// 비밀번호 변경
	void updatePassword(int userIdx, PasswordChangeRequestDTO dto, HttpServletRequest request);
	
	// 이메일 변경
	void updateEmail(int userIdx, EmailChangeRequestDTO dto);
}
