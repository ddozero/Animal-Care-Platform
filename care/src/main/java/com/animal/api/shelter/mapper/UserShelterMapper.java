package com.animal.api.shelter.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.animal.api.shelter.model.request.SearchShelterAnimalRequestDTO;
import com.animal.api.shelter.model.request.SearchShelterRequestDTO;
import com.animal.api.shelter.model.response.AllShelterListResponseDTO;
import com.animal.api.shelter.model.response.ShelterAdoptionReviewResponseDTO;
import com.animal.api.shelter.model.response.ShelterAnimalsResponseDTO;
import com.animal.api.shelter.model.response.ShelterBoardDetailResponseDTO;
import com.animal.api.shelter.model.response.ShelterBoardListResponseDTO;
import com.animal.api.shelter.model.response.ShelterDetailResponseDTO;
import com.animal.api.shelter.model.response.ShelterVolunteerReviewResponseDTO;
import com.animal.api.shelter.model.response.ShelterVolunteersResponseDTO;

@Mapper
public interface UserShelterMapper {

	public List<AllShelterListResponseDTO> getAllShelters(Map map);

	public int getAllSheltersTotalCnt();

	public List<AllShelterListResponseDTO> searchShelters(SearchShelterRequestDTO dto);

	public int searchSheltersTotalCnt(SearchShelterRequestDTO dto);

	public ShelterDetailResponseDTO getShelterDetail(int idx);

	public List<ShelterVolunteersResponseDTO> getShelterVolunteers(Map map);

	public int getShelterVolunteersTotalCnt(int idx);

	public List<ShelterAnimalsResponseDTO> getAllShelterAnimals(Map map);

	public int getAllShelterAnimalsTotalCnt(int idx);

	public List<ShelterAnimalsResponseDTO> searchShelterAnimals(SearchShelterAnimalRequestDTO dto);

	public int searchShelterAnimalsTotalCnt(SearchShelterAnimalRequestDTO dto);

	public List<ShelterBoardListResponseDTO> getShelterBoards(Map map);

	public int getShelterBoardsTotalCnt(int idx);

	public ShelterBoardDetailResponseDTO getShelterBoardDetail(int idx);

	public int incrementViews(int idx);

	public List<ShelterVolunteerReviewResponseDTO> getShelterVolunteerReviews(Map map);
	
	public int getShelterVolunteerReviewsTotalCnt(int idx);

	public List<ShelterAdoptionReviewResponseDTO> getShelterAdoptionReviews(Map map);
	
	public int getShelterAdoptionReviewsTotalCnt(int idx);
}
