package com.animal.api.mypage.information.modify.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.animal.api.auth.exception.CustomException;
import com.animal.api.common.service.CaptchaServiceTest;
import com.animal.api.mypage.information.modify.mapper.InformationModifyMapper;
import com.animal.api.mypage.information.modify.model.request.InformationModifyRequsetDTO;
import com.animal.api.mypage.information.modify.model.request.PasswordChangeRequestDTO;
import com.animal.api.mypage.information.modify.model.response.InformationModifyResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@Primary
@RequiredArgsConstructor
public class InformationModifyServiceImple implements InformationModifyService {

	private final InformationModifyMapper informationModifyMapper;
	private final BCryptPasswordEncoder  passwordEncoder;
	private final CaptchaServiceTest captchaService;  // 추후 프론트 개발 시 실제 캡챠랑 연동
	
	/**
	 * @Value("${google.recaptcha.secret}") 프론트 구현 단계 시 연결 예정
       private String recaptchaSecretKey;  외부 설정으로 주입
	 */
    @Value("${google.recaptcha.secret}")
    private String recaptchaSecretKey; // ← 외부 설정으로 주입

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

	/**
	 * 비밀번호 변경 구현체
	 */
	@Override
	public void updatePassword(int userIdx, PasswordChangeRequestDTO dto, HttpServletRequest request) {
		
		//1.캡챠 겁증 확인
        if (!captchaService.verify(dto.getCaptcha())) {
            throw new CustomException(400, "캡차 검증에 실패했습니다.");
        }
		
		//2.현재 비밀번호 일치 여부 확인
		String currentEncoded = informationModifyMapper.selectEncryptedPassword(userIdx);
		if(!passwordEncoder.matches(dto.getCurrentPassword(), currentEncoded)) {
			throw new CustomException(401, "현재 비밀번호가 일치하지 않습니다.");
		}
		
		//3.새 비밀번호, 확인 일치 여부 확인
		if(!dto.getNewPassword().equals(dto.getConfirmPassword())) {
			throw new CustomException(400, "새 비밀번호와 확인 값이 일치하지 않습니다.");
		}
		
		//4.변경 완료
		String encoded = passwordEncoder.encode(dto.getNewPassword());
		informationModifyMapper.updatePassword(userIdx, encoded);
	}	
}
