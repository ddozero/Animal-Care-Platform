package com.animal.api.email.service;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.auth.exception.CustomException;
import com.animal.api.auth.mapper.AuthMapper;
import com.animal.api.auth.model.vo.UserVO;
import com.animal.api.email.mapper.CertificationMapper;
import com.animal.api.email.model.vo.CertificationVO;

import lombok.RequiredArgsConstructor;

/**
 * 
 * @author Whistler95
 * @since 2025-06-21
 * 회원가입 한 사용자의 이메일 인증 Service
 */
@Service
@Primary
@RequiredArgsConstructor
public class EmailCertificationServiceImple implements EmailCertificationService {

	private final CertificationMapper certificationMapper;
	private final AuthMapper authMapper; // 이메일로 USER_IDX 조회용

	@Override
	public void saveCertification(String email, String token, int minutesValid) {
		UserVO user = authMapper.findByEmail(email);

		if (user == null) {
			throw new CustomException(404, "해당 이메일로 가입된 사용자가 없습니다");
		}

		CertificationVO cert = new CertificationVO();
		cert.setUserIdx(user.getIdx());
		cert.setToken(token);
		cert.setExpiresAt(LocalDateTime.now().plusMinutes(minutesValid));
		cert.setUsed(0);

		certificationMapper.insertCertification(cert);
	}

	@Override
	public boolean verifyCode(String email, String code) {
		email = email.toLowerCase();
		CertificationVO cert = certificationMapper.findLatestValidByEmail(email);

		if (cert == null) {
			throw new CustomException(404, "인증 요청 정보가 없습니다. 인증번호를 다시 요청해주세요.");
		}

		if (!String.valueOf(cert.getToken()).equals(code)) {
			throw new CustomException(401, "인증번호가 일치하지 않습니다.");
		}

		certificationMapper.markAsUsed(cert.getIdx());
		return true;
	}

}
