package com.animal.api.mypage.information.modify.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.auth.exception.CustomException;
import com.animal.api.mypage.information.modify.mapper.InformationModifyMapper;
import com.animal.api.mypage.information.modify.model.request.InformationModifyRequsetDTO;
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

	/**
	 * 내 정보 수정 구현체
	 */
	@Override
	public void updateUserInformation(int userIdx, InformationModifyRequsetDTO dto) {
		try {
			informationModifyMapper.updateUserInfo(userIdx, dto);
		} catch (Exception e) {
			// 닉네임 중복 확인
			if (e.getCause() != null && e.getCause().getMessage().contains("Duplicate entry")
					&& e.getCause().getMessage().contains("NICKNAME")) {
				throw new CustomException(409, "이미 사용중인 닉네임 입니다.");
			}
			throw e;
		}
	}
}
