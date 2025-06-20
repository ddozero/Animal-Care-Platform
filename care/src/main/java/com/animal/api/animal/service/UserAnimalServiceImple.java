package com.animal.api.animal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.animal.mapper.UserAnimalMapper;
import com.animal.api.animal.model.request.AdoptionSubmitReqestDTO;
import com.animal.api.animal.model.request.SearchConditionsRequestDTO;
import com.animal.api.animal.model.response.AdoptionAnimalResponseDTO;
import com.animal.api.animal.model.response.AllAnimalListResponseDTO;
import com.animal.api.animal.model.response.AnimalDetailResponseDTO;

@Service
@Primary
public class UserAnimalServiceImple implements UserAnimalService {

	@Autowired
	private UserAnimalMapper mapper;

	@Override
	public List<AllAnimalListResponseDTO> getAllAnimals(int listSize, int cp) {

		cp = changeCurrentPage(cp, listSize);

		Map<String, Integer> map = new HashMap<String, Integer>();

		map.put("listSize", listSize);
		map.put("cp", cp);

		List<AllAnimalListResponseDTO> animalList = mapper.getAllAnimals(map);

		return animalList;
	}

	@Override
	public List<AllAnimalListResponseDTO> searchAnimals(int listSize, int cp, String type, String breed, String gender,
			int neuter, int age, String adoptionStatus, String personality, int size, String name) {

		cp = changeCurrentPage(cp, listSize);

		SearchConditionsRequestDTO dto = new SearchConditionsRequestDTO(listSize, cp, type, breed, gender, neuter, age,
				adoptionStatus, personality, size, name);
		List<AllAnimalListResponseDTO> animalList = mapper.searchAnimals(dto);

		return animalList;
	}

	@Override
	public AnimalDetailResponseDTO getAnimalDetail(int idx) {
		AnimalDetailResponseDTO dto = mapper.getAnimalDetail(idx);
		return dto;
	}

	@Override
	public AdoptionAnimalResponseDTO getAdoptionInfo(int idx) {
		AdoptionAnimalResponseDTO dto = mapper.getAdoptionInfo(idx);
		return dto;
	}

	@Override
	public int submitAdoption(int userIdx, int animalIdx, String name, String email, String tel, int zipCode,
			String adress, String adressDetail, int hasPet, String description) {
		AdoptionSubmitReqestDTO dto = new AdoptionSubmitReqestDTO(userIdx, animalIdx, name, email, tel, zipCode, adress,
				adressDetail, hasPet, description);
		int count = mapper.submitAdoption(dto);
		return count;
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
