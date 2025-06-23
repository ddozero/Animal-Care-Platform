package com.animal.api.management.animal.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.animal.api.management.animal.model.request.AnimalInsertRequestDTO;
import com.animal.api.management.animal.model.request.AnimalUpdateRequestDTO;
import com.animal.api.management.animal.model.response.AdoptionConsultListResponseDTO;
import com.animal.api.management.animal.model.response.AnimalAddShelterInfoResponseDTO;

public interface ShelterAnimalsService {

	public static int NOT_ANIMAL = 0;
	public static int POST_SUCCESS = 1;
	public static int UPDATE_SUCCESS = 2;
	public static int DELETE_SUCCESS = 3;
	public static int UPLOAD_SUCCESS = 4;
	public static int ERROR = -1;

	public AnimalAddShelterInfoResponseDTO getShelterProfile(int idx);

	public int insertAnimal(AnimalInsertRequestDTO dto);

	public int updateAnimal(AnimalUpdateRequestDTO dto);

	public int deleteAnimal(int idx);

	public int getAnimalShelter(int idx);

	public int uploadAnimalImage(MultipartFile[] files, int idx);

	public List<AdoptionConsultListResponseDTO> getAdoptionConsultList(int idx, int listSize, int cp);

}
