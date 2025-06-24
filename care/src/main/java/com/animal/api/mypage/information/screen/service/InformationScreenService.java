package com.animal.api.mypage.information.screen.service;

import javax.servlet.http.HttpServletRequest;

import com.animal.api.mypage.information.screen.model.response.InformationScreenResponseDTO;

public interface InformationScreenService {

	InformationScreenResponseDTO getMypageSummary(HttpServletRequest request);
	
}
