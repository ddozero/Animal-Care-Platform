package com.animal.api.find.shelter.service;

import com.animal.api.find.model.response.FindUserIdResponseDTO;
import com.animal.api.find.shelter.model.request.FindShelterIdRequestDTO;
import com.animal.api.find.shelter.model.request.FindShelterPasswordResetRequestDTO;
import com.animal.api.find.shelter.model.request.FindShelterPasswordVerifyRequestDTO;

public interface FindShelterService {

	FindUserIdResponseDTO findShelterId(FindShelterIdRequestDTO dto);

	void initShelterPasswordReset(String userid);

	void verifyShelterPasswordResetCode(FindShelterPasswordVerifyRequestDTO dto);

	void resetShelterPassword(FindShelterPasswordResetRequestDTO dto);
}
