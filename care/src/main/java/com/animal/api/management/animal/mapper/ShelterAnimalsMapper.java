package com.animal.api.management.animal.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.animal.api.management.animal.model.request.AnimalInsertRequestDTO;
import com.animal.api.management.animal.model.response.AnimalAddShelterInfoResponseDTO;

@Mapper
public interface ShelterAnimalsMapper {

	public AnimalAddShelterInfoResponseDTO getShelterProfile(int idx);

	public int insertAnimal(AnimalInsertRequestDTO dto);

}
