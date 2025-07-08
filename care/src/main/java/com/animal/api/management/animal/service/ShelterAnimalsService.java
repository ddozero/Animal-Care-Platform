package com.animal.api.management.animal.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.animal.api.common.model.PageInformationDTO;
import com.animal.api.management.animal.model.request.AdoptionConsultStatusRequestDTO;
import com.animal.api.management.animal.model.request.AnimalInsertRequestDTO;
import com.animal.api.management.animal.model.request.AnimalUpdateRequestDTO;
import com.animal.api.management.animal.model.response.AdoptionConsultDetailResponseDTO;
import com.animal.api.management.animal.model.response.AdoptionConsultListResponseDTO;
import com.animal.api.management.animal.model.response.AnimalAddShelterInfoResponseDTO;

public interface ShelterAnimalsService {

	public static int POST_SUCCESS = 1;
	public static int UPDATE_SUCCESS = 2;
	public static int DELETE_SUCCESS = 3;
	public static int UPLOAD_SUCCESS = 4;
	public static int NOT_ANIMAL = 5;
	public static int NOT_OWNED_ANIMAL = 6;
	public static int NOT_CONSULT = 7;
	public static int NOT_OWNED_CONSULT = 8;
	public static int ERROR = -1;

	public AnimalAddShelterInfoResponseDTO getShelterProfile(int idx);

	public int insertAnimal(AnimalInsertRequestDTO dto, int userIdx);

	public int updateAnimal(AnimalUpdateRequestDTO dto, int animalIdx, int userIdx);

	public int deleteAnimal(int animalIdx, int userIdx);

	public int getAnimalShelter(int idx);

	public int uploadAnimalImage(MultipartFile[] files, int idx, String method);

	public List<AdoptionConsultListResponseDTO> getAdoptionConsultList(int idx, int cp);

	public PageInformationDTO getAdoptionConsultListPageInfo(int idx, int cp);

	public AdoptionConsultDetailResponseDTO getAdoptionConsultDetail(int idx);

	public int updateAdoptionConsultStatus(AdoptionConsultStatusRequestDTO dto, int userIdx, int consultIdx);

}
