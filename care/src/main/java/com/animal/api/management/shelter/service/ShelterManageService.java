package com.animal.api.management.shelter.service;

import com.animal.api.management.shelter.model.request.ShelterInfoUpdateRequestDTO;
import com.animal.api.management.shelter.model.response.AllManageShelterResponseDTO;
import com.animal.api.management.shelter.model.response.ShelterVolunteerReviewResponseDTO;

public interface ShelterManageService {

	public AllManageShelterResponseDTO getShelterInfo(int idx);
	
	public int updateSheterInfo(ShelterInfoUpdateRequestDTO dto);
	
	public ShelterVolunteerReviewResponseDTO getVolunteerReview(int idx);

}
