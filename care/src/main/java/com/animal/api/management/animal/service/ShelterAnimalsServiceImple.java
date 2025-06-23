package com.animal.api.management.animal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.animal.api.common.util.FileManager;
import com.animal.api.management.animal.mapper.ShelterAnimalsMapper;
import com.animal.api.management.animal.model.request.AnimalInsertRequestDTO;
import com.animal.api.management.animal.model.request.AnimalUpdateRequestDTO;
import com.animal.api.management.animal.model.response.AdoptionConsultListResponseDTO;
import com.animal.api.management.animal.model.response.AnimalAddShelterInfoResponseDTO;

@Service
@Primary
public class ShelterAnimalsServiceImple implements ShelterAnimalsService {

	@Autowired
	private ShelterAnimalsMapper mapper;
	@Autowired
	private FileManager fileManager;

	@Override
	public AnimalAddShelterInfoResponseDTO getShelterProfile(int idx) {
		AnimalAddShelterInfoResponseDTO dto = mapper.getShelterProfile(idx);
		return dto;
	}

	@Override
	public int insertAnimal(AnimalInsertRequestDTO dto) {
		int result = mapper.insertAnimal(dto);

		result = result > 0 ? POST_SUCCESS : ERROR;
		return result;
	}

	@Override
	public int updateAnimal(AnimalUpdateRequestDTO dto) {
		int result = mapper.updateAnimal(dto);

		result = result > 0 ? UPDATE_SUCCESS : ERROR;
		return result;
	}

	@Override
	public int deleteAnimal(int idx) {
		int result = mapper.deleteAnimal(idx);

		result = result > 0 ? DELETE_SUCCESS : ERROR;

		if (result == DELETE_SUCCESS) {
			fileManager.deleteFolder("animals", idx);
		}

		return result;
	}

	@Override
	public int getAnimalShelter(int idx) {
		Integer userIdx = mapper.getAnimalShelter(idx);

		if (userIdx == null) {
			return NOT_ANIMAL;
		}

		return userIdx;
	}

	@Override
	public int uploadAnimalImage(MultipartFile[] files, int idx) {

		boolean result = fileManager.uploadImages("animals", idx, files);

		if (result) {
			return UPLOAD_SUCCESS;
		} else {
			mapper.deleteAnimal(idx);
			return ERROR;
		}
	}

	@Override
	public List<AdoptionConsultListResponseDTO> getAdoptionConsultList(int idx, int listSize, int cp) {
		cp = changeCurrentPage(cp, listSize);

		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("idx", idx);
		map.put("listSize", listSize);
		map.put("cp", cp);

		List<AdoptionConsultListResponseDTO> consultList = mapper.getAdoptionConsultList(map);

		return consultList;
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
