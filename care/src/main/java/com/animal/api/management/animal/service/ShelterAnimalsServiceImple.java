package com.animal.api.management.animal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.management.animal.mapper.ShelterAnimalsMapper;
import com.animal.api.management.animal.model.request.AnimalInsertRequestDTO;
import com.animal.api.management.animal.model.request.AnimalUpdateRequestDTO;
import com.animal.api.management.animal.model.response.AnimalAddShelterInfoResponseDTO;

@Service
@Primary
public class ShelterAnimalsServiceImple implements ShelterAnimalsService {

	@Autowired
	private ShelterAnimalsMapper mapper;

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
		return result;
	}
	
	@Override
	public int getAnimalShelter(int idx) {
		int userIdx = mapper.getAnimalShelter(idx);
		return userIdx;
	}
}
