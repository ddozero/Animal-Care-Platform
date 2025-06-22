package com.animal.api.management.shelter.service;

import java.util.List;

import com.animal.api.management.shelter.model.request.ShelterInfoUpdateRequestDTO;
import com.animal.api.management.shelter.model.response.AllManageShelterResponseDTO;
import com.animal.api.management.shelter.model.response.ManageVolunteerReviewResponseDTO;

public interface ShelterManageService {

	public AllManageShelterResponseDTO getShelterInfo(int idx);
	
	public int updateSheterInfo(ShelterInfoUpdateRequestDTO dto);
	
	public List<ManageVolunteerReviewResponseDTO> getVolunteerReviews(int listSize, int cp, int idx);

}
