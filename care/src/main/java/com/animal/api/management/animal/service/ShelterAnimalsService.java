package com.animal.api.management.animal.service;

import java.util.Map;

import com.animal.api.management.animal.model.request.AnimalInsertRequestDTO;
import com.animal.api.management.animal.model.response.AnimalAddShelterInfoResponseDTO;

public interface ShelterAnimalsService {
	
	public static int POST_SUCCESS = 1;
	public static int ERROR = -1;

	public AnimalAddShelterInfoResponseDTO getShelterProfile(int idx);

	public int insertAnimal(AnimalInsertRequestDTO dto);

}
