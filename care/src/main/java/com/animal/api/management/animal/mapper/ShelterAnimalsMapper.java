package com.animal.api.management.animal.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.animal.api.management.animal.model.request.AnimalInsertRequestDTO;
import com.animal.api.management.animal.model.request.AnimalUpdateRequestDTO;
import com.animal.api.management.animal.model.response.AdoptionConsultListResponseDTO;
import com.animal.api.management.animal.model.response.AnimalAddShelterInfoResponseDTO;

@Mapper
public interface ShelterAnimalsMapper {

	public AnimalAddShelterInfoResponseDTO getShelterProfile(int idx);

	public int insertAnimal(AnimalInsertRequestDTO dto);

	public int updateAnimal(AnimalUpdateRequestDTO dto);

	public int deleteAnimal(int idx);
	
	public Integer getAnimalShelter(int idx);
	
	public Integer getAnimalMaxIdx();
	
	public AdoptionConsultListResponseDTO getAdoptionConsultList(Map map);

}
