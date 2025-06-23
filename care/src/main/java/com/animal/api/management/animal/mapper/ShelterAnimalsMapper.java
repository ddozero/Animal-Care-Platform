package com.animal.api.management.animal.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.animal.api.management.animal.model.request.AdoptionConsultStatusRequestDTO;
import com.animal.api.management.animal.model.request.AnimalInsertRequestDTO;
import com.animal.api.management.animal.model.request.AnimalUpdateRequestDTO;
import com.animal.api.management.animal.model.response.AdoptionConsultDetailResponseDTO;
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

	public List<AdoptionConsultListResponseDTO> getAdoptionConsultList(Map map);

	public AdoptionConsultDetailResponseDTO getAdoptionConsultDetail(int idx);

	public int updateAdoptionConsultStatus(AdoptionConsultStatusRequestDTO dto);
	
	public Integer checkAdoptionConsultShelter(int idx);

}
