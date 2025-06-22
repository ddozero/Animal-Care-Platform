package com.animal.api.signup.service;

import com.animal.api.signup.model.request.UserSignupRequestDTO;

public interface UserSignupService {

    void signupUser(UserSignupRequestDTO dto);
}
