package com.animal.api.management.shelter.service;

import com.animal.api.management.shelter.model.request.ShelterInfoUpdateRequestDTO;
import com.animal.api.management.shelter.model.response.AllManageShelterResponseDTO;

public interface ShelterManageService {

	public AllManageShelterResponseDTO getShelterInfo(int idx);
	
	public int updateSheterInfo(ShelterInfoUpdateRequestDTO dto);

}
