package com.animal.api.mypage.information.modify.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.mypage.information.modify.mapper.InformationModifyMapper;
import com.animal.api.mypage.information.modify.model.response.InformationModifyResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@Primary
@RequiredArgsConstructor
public class InformationModifyServiceImple implements InformationModifyService {

	private final InformationModifyMapper informationModifyMapper;
	
	/**
	 * 내 정보 출력 구현체
	 */
	@Override
	public InformationModifyResponseDTO getUserInformation(int userIdx) {
	return informationModifyMapper.selectUserInfo(userIdx);
	}
}
