package com.animal.api.auth.service;

import com.animal.api.auth.model.request.LoginRequestDTO;
import com.animal.api.auth.model.response.LoginResponseDTO;

public interface AuthService {

	//일반 사용자 로그인
	LoginResponseDTO login(LoginRequestDTO dto);
	
	//관리자 로그인
	LoginResponseDTO loginAdmin(LoginRequestDTO dto);
}
