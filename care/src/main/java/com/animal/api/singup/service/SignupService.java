package com.animal.api.singup.service;

import com.animal.api.singup.model.request.UserSignupRequestDTO;

public interface SignupService {

	 void signupUser(UserSignupRequestDTO dto);
}
