package com.animal.api.management.animal.service;

import com.animal.api.management.animal.model.response.AnimalAddShelterInfoResponseDTO;

public interface ShelterAnimalsService {
	
	public AnimalAddShelterInfoResponseDTO getShelterProfile(int idx);
	
}
