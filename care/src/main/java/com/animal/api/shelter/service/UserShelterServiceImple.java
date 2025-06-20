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
import com.animal.api.shelter.model.response.AllShelterListDTO;
import com.animal.api.shelter.model.response.ShelterAnimalsDTO;
import com.animal.api.shelter.model.response.ShelterDetailDTO;
import com.animal.api.shelter.model.response.ShelterVolunteersDTO;

@Service
@Primary
public class UserShelterServiceImple implements UserShelterService {

	@Autowired
	private UserShelterMapper mapper;

	@Override
	public List<AllShelterListDTO> getAllShelters(int listSize, int cp) {
		cp = changeCurrentPage(cp, listSize);

		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("listSize", listSize);
		map.put("cp", cp);

		List<AllShelterListDTO> shelterList = mapper.getAllShelters(map);

		return shelterList;
	}

	@Override
	public List<AllShelterListDTO> searchShelters(int listSize, int cp, String shelterName, String shelterAddress,
			String shelterType) {
		cp = changeCurrentPage(cp, listSize);
		SearchShelterRequestDTO dto = new SearchShelterRequestDTO(listSize, cp, shelterName, shelterAddress,
				shelterType);

		List<AllShelterListDTO> shelterList = mapper.searchShelters(dto);

		return shelterList;
	}

	@Override
	public ShelterDetailDTO getShelterDetail(int idx) {
		ShelterDetailDTO dto = mapper.getShelterDetail(idx);
		return dto;
	}

	@Override
	public List<ShelterVolunteersDTO> getShelterVolunteers(int listSize, int cp, int idx) {
		cp = changeCurrentPage(cp, listSize);

		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("listSize", listSize);
		map.put("cp", cp);
		map.put("idx", idx);

		List<ShelterVolunteersDTO> volunteerList = mapper.getShelterVolunteers(map);

		return volunteerList;
	}

	@Override
	public List<ShelterAnimalsDTO> getAllShelterAnimals(int listSize, int cp, int idx) {
		cp = changeCurrentPage(cp, listSize);

		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("listSize", listSize);
		map.put("cp", cp);
		map.put("idx", idx);

		List<ShelterAnimalsDTO> animalList = mapper.getAllShelterAnimals(map);

		return animalList;
	}

	@Override
	public List<ShelterAnimalsDTO> searchShelterAnimals(int idx, int listSize, int cp, String type, String breed,
			String gender, int neuter, int age, String adoptionStatus, String personality, int size, String name) {
		cp = changeCurrentPage(cp, listSize);

		SearchShelterAnimalRequestDTO dto = new SearchShelterAnimalRequestDTO(idx, listSize, cp, type, breed, gender,
				neuter, age, adoptionStatus, personality, size, name);

		List<ShelterAnimalsDTO> animalList = mapper.searchShelterAnimals(dto);

		return animalList;
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
