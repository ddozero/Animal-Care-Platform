package com.animal.api.signup.service;

import org.springframework.web.multipart.MultipartFile;

import com.animal.api.signup.model.request.ShelterSignupRequestDTO;

public interface ShelterSignupService {

	void signupShelter(ShelterSignupRequestDTO dto, MultipartFile businessFile);
}
