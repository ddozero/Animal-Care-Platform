package com.animal.api.mypage.information.modify.service;

import com.animal.api.mypage.information.modify.model.response.InformationModifyResponseDTO;

public interface InformationModifyService {
	InformationModifyResponseDTO getUserInformation(int userIdx);
}
