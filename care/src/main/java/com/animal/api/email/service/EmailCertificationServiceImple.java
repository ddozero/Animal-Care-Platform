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

@Service
@Primary
@RequiredArgsConstructor
public class EmailCertificationServiceImple implements EmailCertificationService {

	private final CertificationMapper certificationMapper;
	private final AuthMapper authMapper; //이메일로 USER_IDX 조회용
	
	@Override
	public void saveCertification(String email, String token, int minutesValid) {
		UserVO user = authMapper.findByEmail(email);
		
		if(user == null) {
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
		CertificationVO cert = certificationMapper.findLatestValidByEmail(email);
		
		if(cert == null || !cert.getToken().equals(code)) {
			return false;
		}
			
		//이베일 인증 성공 시 사용 처리 
		certificationMapper.markAsUsed(cert.getIdx());
		return true;
	}

}
