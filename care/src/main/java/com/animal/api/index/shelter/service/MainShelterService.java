package com.animal.api.index.shelter.service;

import java.util.List;

import com.animal.api.index.shelter.model.response.MainShelterResponseDTO;

public interface MainShelterService {
	List<MainShelterResponseDTO> getSheltersByRegion(String region);
	int countSheltersByRegion(String region);
	
	int countAllShelters();
}
