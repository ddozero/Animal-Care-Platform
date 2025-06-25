package com.animal.api.admin.shelter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.common.util.FileManager;
import com.animal.api.management.animal.mapper.ShelterAnimalsMapper;

@Service
@Primary
public class AdminShelterServiceImple implements AdminShelterService {
	@Autowired
	private ShelterAnimalsMapper shelterAnimalsMapper;
	@Autowired
	private FileManager fileManager;

	@Override
	public int deleteAnimal(int idx) {
		Integer checkUserIdx = shelterAnimalsMapper.getAnimalShelter(idx);
		if (checkUserIdx == null) {// 해당 상담신청이 있는지 검증
			return NOT_ANIMAL;
		}

		int result = shelterAnimalsMapper.deleteAnimal(idx);
		result = result > 0 ? DELETE_SUCCESS : ERROR;

		if (result == DELETE_SUCCESS) {
			fileManager.deleteFolder("animals", idx);
		}
		return result;
	}
}
