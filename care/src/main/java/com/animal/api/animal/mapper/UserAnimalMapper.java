package com.animal.api.animal.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.animal.api.animal.model.request.AdoptionSubmitReqestDTO;
import com.animal.api.animal.model.request.SearchConditionsRequestDTO;
import com.animal.api.animal.model.response.AdoptionAnimalResponseDTO;
import com.animal.api.animal.model.response.AllAnimalListResponseDTO;
import com.animal.api.animal.model.response.AnimalDetailResponseDTO;

@Mapper
public interface UserAnimalMapper {
	public List<AllAnimalListResponseDTO> getAllAnimals(Map map);

	public int getAllAnimalsTotalCnt();

	public List<AllAnimalListResponseDTO> searchAnimals(SearchConditionsRequestDTO dto);

	public int searchAnimalsTotalCnt(SearchConditionsRequestDTO dto);

	public AnimalDetailResponseDTO getAnimalDetail(int idx);

	public AdoptionAnimalResponseDTO getAdoptionInfo(int idx);

	public Integer checkAdoptionStatus(int idx);

	public Integer checkDuplicateUser(Map map);

	public int submitAdoption(AdoptionSubmitReqestDTO dto);

}
