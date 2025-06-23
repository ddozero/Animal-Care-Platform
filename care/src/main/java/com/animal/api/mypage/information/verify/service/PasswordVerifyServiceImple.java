package com.animal.api.mypage.information.verify.service;

import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.animal.api.mypage.information.verify.mapper.PasswordVerifyMapper;

import lombok.RequiredArgsConstructor;

@Service
@Primary
@RequiredArgsConstructor
public class PasswordVerifyServiceImple implements PasswordVerifyService {

	private final PasswordVerifyMapper passwordVerifyMapper;
	private final BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public boolean verifyPassword(int userIdx, String rawPassword) {
		String encrytedPassword = passwordVerifyMapper.selectEncryptedPassword(userIdx);
		return passwordEncoder.matches(rawPassword, encrytedPassword);
	}
}
