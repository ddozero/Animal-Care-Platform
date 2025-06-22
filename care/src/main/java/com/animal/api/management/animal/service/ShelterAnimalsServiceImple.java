package com.animal.api.management.animal.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.management.animal.mapper.ShelterAnimalsMapper;
import com.animal.api.management.animal.model.request.AnimalInsertRequestDTO;
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
	public Map insertAnimal(AnimalInsertRequestDTO dto) {
		Map map = new HashMap();
		int result = 0;
		String msg = null;
		Boolean errorCheck = false;
		if (dto.getUserIdx() == 0) {
			result = NOT_USER;
			msg = "잘못된 접근: 유저 정보가 없습니다.";
			errorCheck = true;
		} else if (dto.getAdoptionStatusIdx() == 0) {
			result = NOT_ADOPTION_STATUS;
			msg = "잘못된 접근: 입양 상태 정보가 없습니다.";
			errorCheck = true;
		} else if (dto.getAnimalBreedIdx() == 0) {
			result = NOT_ANIMAL_BREED;
			msg = "잘못된 접근: 동물 품종 정보가 없습니다.";
			errorCheck = true;
		} else if (dto.getAnimalPersonalityIdx() == 0) {
			result = NOT_ANIMAL_PERSONALITY;
			msg = "잘못된 접근: 동물 성격 정보가가 없습니다.";
			errorCheck = true;
		} else if (dto.getName() == null || dto.getName().equals("")) {
			result = NOT_NAME;
			msg = "잘못된 접근: 이름이 입력되지 않았습니다.";
			errorCheck = true;
		} else if (dto.getGender() == null || (!"M".equals(dto.getGender()) && !"F".equals(dto.getGender()))) {
			result = NOT_GENDER;
			msg = "잘못된 접근: 성별이 잘못 입력되었습니다. (M/F)";
			errorCheck = true;
		} else if (dto.getAge() < 0) {
			result = NOT_AGE;
			msg = "잘못된 접근: 나이 정보가 누락되었거나 잘못되었습니다.";
			errorCheck = true;
		} else if (dto.getSize() < 0) {
			result = NOT_SIZE;
			msg = "잘못된 접근: 사이즈 정보가 누락되었거나 잘못되었습니다.";
			errorCheck = true;
		} else if (dto.getNeuter() != 0 && dto.getNeuter() != 1) {
			result = NOT_NEUTER;
			msg = "잘못된 접근: 중성화 여부가 잘못되었습니다. (0: X, 1: O)";
			errorCheck = true;
		} else if (dto.getDescription() == null || dto.getDescription().equals("")) {
			result = NOT_DESCRIPTION;
			msg = "잘못된 접근: 설명 내용이 없습니다.";
			errorCheck = true;
		}

		if (!errorCheck) {
			int count = mapper.insertAnimal(dto);
			if (count > 0) {
				result = POST_SUCCESS;
				msg = "유기동물 등록 성공";
			} else {
				result = ERROR;
				msg = "잘못된 접근";
			}
		}

		map.put("result", result);
		map.put("msg", msg);

		return map;
	}
}
