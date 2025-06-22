package com.animal.api.management.animal.service;

import java.util.Map;

import com.animal.api.management.animal.model.request.AnimalInsertRequestDTO;
import com.animal.api.management.animal.model.response.AnimalAddShelterInfoResponseDTO;

public interface ShelterAnimalsService {
	
	public static int POST_SUCCESS = 1;
	public static int NOT_USER = 1001;
	public static int NOT_ADOPTION_STATUS = 1002;
	public static int NOT_ANIMAL_BREED = 1003;
	public static int NOT_ANIMAL_PERSONALITY = 1004;
	public static int NOT_NAME = 1005;
	public static int NOT_GENDER = 1006;
	public static int NOT_AGE = 1007;
	public static int NOT_SIZE = 1008;
	public static int NOT_NEUTER = 1009;
	public static int NOT_DESCRIPTION = 1010;
	public static int ERROR = -1;

	public AnimalAddShelterInfoResponseDTO getShelterProfile(int idx);

	public Map insertAnimal(AnimalInsertRequestDTO dto);

}
