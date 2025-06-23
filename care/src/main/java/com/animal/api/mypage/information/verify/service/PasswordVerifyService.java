package com.animal.api.mypage.information.verify.service;

public interface PasswordVerifyService {

	//내 정보 수정 비밀번호 확인
	boolean verifyPassword(int userIdx, String rawPassword);
}
