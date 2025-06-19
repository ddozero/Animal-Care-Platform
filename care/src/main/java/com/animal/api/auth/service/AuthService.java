package com.animal.api.auth.service;

import com.animal.api.auth.model.request.LoginRequestDTO;
import com.animal.api.auth.model.response.LoginResponseDTO;

public interface AuthService {

	LoginResponseDTO login(LoginRequestDTO dto);
}
