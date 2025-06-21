package com.animal.api.shelter.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.animal.api.shelter.model.request.SearchShelterAnimalRequestDTO;
import com.animal.api.shelter.model.request.SearchShelterRequestDTO;
import com.animal.api.shelter.model.response.AllShelterListResponseDTO;
import com.animal.api.shelter.model.response.ShelterAnimalsResponseDTO;
import com.animal.api.shelter.model.response.ShelterBoardDetailResponseDTO;
import com.animal.api.shelter.model.response.ShelterBoardListResponseDTO;
import com.animal.api.shelter.model.response.ShelterDetailResponseDTO;
import com.animal.api.shelter.model.response.ShelterVolunteersResponseDTO;

@Mapper
public interface UserShelterMapper {

	public List<AllShelterListResponseDTO> getAllShelters(Map map);

	public List<AllShelterListResponseDTO> searchShelters(SearchShelterRequestDTO dto);

	public ShelterDetailResponseDTO getShelterDetail(int idx);

	public List<ShelterVolunteersResponseDTO> getShelterVolunteers(Map map);

	public List<ShelterAnimalsResponseDTO> getAllShelterAnimals(Map map);

	public List<ShelterAnimalsResponseDTO> searchShelterAnimals(SearchShelterAnimalRequestDTO dto);

	public List<ShelterBoardListResponseDTO> getShelterBoards(Map map);

	public ShelterBoardDetailResponseDTO getShelterBoardDetail(int idx);
}
