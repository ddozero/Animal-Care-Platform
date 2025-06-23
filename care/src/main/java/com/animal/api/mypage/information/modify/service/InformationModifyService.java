package com.animal.api.mypage.information.modify.service;

import com.animal.api.mypage.information.modify.model.request.InformationModifyRequsetDTO;
import com.animal.api.mypage.information.modify.model.response.InformationModifyResponseDTO;

public interface InformationModifyService {
	// 내 정보 출력
	InformationModifyResponseDTO getUserInformation(int userIdx);

	// 내 정보 수정
	void updateUserInformation(int userIdx, InformationModifyRequsetDTO dto);
}
