package com.animal.api.shelter.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.shelter.mapper.UserShelterMapper;
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

@Service
@Primary
public class UserShelterServiceImple implements UserShelterService {

	@Autowired
	private UserShelterMapper mapper;

	@Override
	public List<AllShelterListResponseDTO> getAllShelters(int listSize, int cp) {
		cp = changeCurrentPage(cp, listSize);

		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("listSize", listSize);
		map.put("cp", cp);

		List<AllShelterListResponseDTO> shelterList = mapper.getAllShelters(map);

		return shelterList;
	}

	@Override
	public List<AllShelterListResponseDTO> searchShelters(int listSize, int cp, String shelterName,
			String shelterAddress, String shelterType) {
		cp = changeCurrentPage(cp, listSize);
		SearchShelterRequestDTO dto = new SearchShelterRequestDTO(listSize, cp, shelterName, shelterAddress,
				shelterType);

		List<AllShelterListResponseDTO> shelterList = mapper.searchShelters(dto);

		return shelterList;
	}

	@Override
	public ShelterDetailResponseDTO getShelterDetail(int idx) {
		ShelterDetailResponseDTO dto = mapper.getShelterDetail(idx);
		return dto;
	}

	@Override
	public List<ShelterVolunteersResponseDTO> getShelterVolunteers(int listSize, int cp, int idx) {
		cp = changeCurrentPage(cp, listSize);

		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("listSize", listSize);
		map.put("cp", cp);
		map.put("idx", idx);

		List<ShelterVolunteersResponseDTO> volunteerList = mapper.getShelterVolunteers(map);

		return volunteerList;
	}

	@Override
	public List<ShelterAnimalsResponseDTO> getAllShelterAnimals(int listSize, int cp, int idx) {
		cp = changeCurrentPage(cp, listSize);

		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("listSize", listSize);
		map.put("cp", cp);
		map.put("idx", idx);

		List<ShelterAnimalsResponseDTO> animalList = mapper.getAllShelterAnimals(map);

		return animalList;
	}

	@Override
	public List<ShelterAnimalsResponseDTO> searchShelterAnimals(int idx, int listSize, int cp, String type,
			String breed, String gender, int neuter, int age, String adoptionStatus, String personality, int size,
			String name) {
		cp = changeCurrentPage(cp, listSize);

		SearchShelterAnimalRequestDTO dto = new SearchShelterAnimalRequestDTO(idx, listSize, cp, type, breed, gender,
				neuter, age, adoptionStatus, personality, size, name);

		List<ShelterAnimalsResponseDTO> animalList = mapper.searchShelterAnimals(dto);

		return animalList;
	}

	@Override
	public List<ShelterBoardListResponseDTO> getShelterBoards(int listSize, int cp, int idx) {
		cp = changeCurrentPage(cp, listSize);

		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("listSize", listSize);
		map.put("cp", cp);
		map.put("idx", idx);

		List<ShelterBoardListResponseDTO> boardList = mapper.getShelterBoards(map);

		return boardList;
	}

	@Override
	public ShelterBoardDetailResponseDTO getShelterBoardDetail(int idx) {
		int result = mapper.incrementViews(idx);

		if (result > 0) {
			ShelterBoardDetailResponseDTO dto = mapper.getShelterBoardDetail(idx);
			return dto;
		} else {
			return null;
		}
	}

	@Override
	public List<ShelterVolunteerReviewResponseDTO> getShelterVolunteerReviews(int listSize, int cp, int idx) {
		cp = changeCurrentPage(cp, listSize);

		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("listSize", listSize);
		map.put("cp", cp);
		map.put("idx", idx);

		List<ShelterVolunteerReviewResponseDTO> reviewList = mapper.getShelterVolunteerReviews(map);

		return reviewList;
	}

	@Override
	public List<ShelterAdoptionReviewResponseDTO> getShelterAdoptionReviews(int listSize, int cp, int idx) {
		cp = changeCurrentPage(cp, listSize);

		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("listSize", listSize);
		map.put("cp", cp);
		map.put("idx", idx);

		List<ShelterAdoptionReviewResponseDTO> reviewList = mapper.getShelterAdoptionReviews(map);

		return reviewList;
	}

	// 넘어온 페이지를 쿼리에 넣을 수 있게 가공하는 메서드
	public int changeCurrentPage(int cp, int listSize) {
		if (cp == 0) {
			cp = 1;
		}
		cp = (cp - 1) * listSize;

		return cp;
	}
}
