package com.animal.api.management.animal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.animal.api.common.util.FileManager;
import com.animal.api.management.animal.mapper.ShelterAnimalsMapper;
import com.animal.api.management.animal.model.request.AnimalInsertRequestDTO;
import com.animal.api.management.animal.model.request.AnimalUpdateRequestDTO;
import com.animal.api.management.animal.model.response.AnimalAddShelterInfoResponseDTO;

@Service
@Primary
public class ShelterAnimalsServiceImple implements ShelterAnimalsService {

	@Autowired
	private ShelterAnimalsMapper mapper;
	@Autowired
	private FileManager fileManager;

	@Override
	public AnimalAddShelterInfoResponseDTO getShelterProfile(int idx) {
		AnimalAddShelterInfoResponseDTO dto = mapper.getShelterProfile(idx);
		return dto;
	}

	@Override
	public int insertAnimal(AnimalInsertRequestDTO dto) {
		int result = mapper.insertAnimal(dto);

		result = result > 0 ? POST_SUCCESS : ERROR;
		return result;
	}

	@Override
	public int updateAnimal(AnimalUpdateRequestDTO dto) {
		int result = mapper.updateAnimal(dto);

		result = result > 0 ? UPDATE_SUCCESS : ERROR;
		return result;
	}

	@Override
	public int deleteAnimal(int idx) {
		int result = mapper.deleteAnimal(idx);

		result = result > 0 ? DELETE_SUCCESS : ERROR;
		
		if(result == DELETE_SUCCESS) {
			fileManager.deleteFolder("animals", idx);
		}
		
		return result;
	}

	@Override
	public int getAnimalShelter(int idx) {
		Integer userIdx = mapper.getAnimalShelter(idx);

		if (userIdx == null) {
			return NOT_ANIMAL;
		}

		return userIdx;
	}

	@Override
	public int uploadAnimalImage(MultipartFile[] files, int idx) {

		boolean result = fileManager.uploadImages("animals", idx, files);

		if (result) {
			return UPLOAD_SUCCESS;
		} else {
			mapper.deleteAnimal(idx);
			return ERROR;
		}
	}
}
