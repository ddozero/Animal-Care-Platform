package com.animal.api.signup.service;

import com.animal.api.signup.model.request.UserSignupRequestDTO;

public interface SignupService {

	void signupUser(UserSignupRequestDTO dto);
}
