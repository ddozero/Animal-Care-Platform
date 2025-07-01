package com.animal.api.management.animal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.animal.api.common.model.PageInformationDTO;
import com.animal.api.common.util.FileManager;
import com.animal.api.management.animal.mapper.ShelterAnimalsMapper;
import com.animal.api.management.animal.model.request.AdoptionConsultStatusRequestDTO;
import com.animal.api.management.animal.model.request.AnimalInsertRequestDTO;
import com.animal.api.management.animal.model.request.AnimalUpdateRequestDTO;
import com.animal.api.management.animal.model.response.AdoptionConsultDetailResponseDTO;
import com.animal.api.management.animal.model.response.AdoptionConsultListResponseDTO;
import com.animal.api.management.animal.model.response.AnimalAddShelterInfoResponseDTO;

@Service
@Primary
public class ShelterAnimalsServiceImple implements ShelterAnimalsService {

	@Autowired
	private ShelterAnimalsMapper mapper;
	@Autowired
	private FileManager fileManager;

	private int listSize = 15;
	private int pageSize = 5;

	@Override
	public AnimalAddShelterInfoResponseDTO getShelterProfile(int idx) {
		AnimalAddShelterInfoResponseDTO dto = mapper.getShelterProfile(idx);
		return dto;
	}

	@Override
	public int insertAnimal(AnimalInsertRequestDTO dto, int userIdx) {
		dto.setUserIdx(userIdx);
		int result = mapper.insertAnimal(dto);

		result = result > 0 ? POST_SUCCESS : ERROR;
		return result;
	}

	@Override
	public int updateAnimal(AnimalUpdateRequestDTO dto, int animalIdx, int userIdx) {
		Integer checkUserIdx = mapper.getAnimalShelter(animalIdx);
		if (checkUserIdx == null) {// 해당 상담신청이 있는지 검증
			return NOT_ANIMAL;
		}
		if (checkUserIdx != userIdx) { // 해당 보호시설의 유기동물이 맞는지 검증
			return NOT_OWNED_ANIMAL;
		}
		dto.setIdx(animalIdx);

		int result = mapper.updateAnimal(dto);
		result = result > 0 ? UPDATE_SUCCESS : ERROR;
		return result;
	}

	@Override
	public int deleteAnimal(int animalIdx, int userIdx) {
		Integer checkUserIdx = mapper.getAnimalShelter(animalIdx);
		if (checkUserIdx == null) {// 해당 상담신청이 있는지 검증
			return NOT_ANIMAL;
		}
		if (checkUserIdx != userIdx) { // 해당 보호시설의 유기동물이 맞는지 검증
			return NOT_OWNED_ANIMAL;
		}

		int result = mapper.deleteAnimal(animalIdx);
		result = result > 0 ? DELETE_SUCCESS : ERROR;

		if (result == DELETE_SUCCESS) {
			fileManager.deleteFolder("animals", animalIdx);
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
	public int uploadAnimalImage(MultipartFile[] files, int idx, String method) {

		boolean result = fileManager.uploadImages("animals", idx, files);

		if (result) {
			return UPLOAD_SUCCESS;
		} else {
			if (method.equals("insert")) {
				mapper.deleteAnimal(idx);
			}
			return ERROR;
		}
	}

	@Override
	public List<AdoptionConsultListResponseDTO> getAdoptionConsultList(int idx, int cp) {
		cp = changeCurrentPage(cp, listSize);

		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("idx", idx);
		map.put("listSize", listSize);
		map.put("cp", cp);

		List<AdoptionConsultListResponseDTO> consultList = mapper.getAdoptionConsultList(map);

		return consultList;
	}

	@Override
	public PageInformationDTO getAdoptionConsultListPageInfo(int idx, int cp) {
		if (cp == 0) {
			cp = 1;
		}
		int totalCnt = mapper.getAdoptionConsultListTotalCnt(idx);
		PageInformationDTO pageInfo = new PageInformationDTO(totalCnt, listSize, pageSize, cp);
		return pageInfo;
	}

	@Override
	public AdoptionConsultDetailResponseDTO getAdoptionConsultDetail(int idx) {
		AdoptionConsultDetailResponseDTO dto = mapper.getAdoptionConsultDetail(idx);
		return dto;
	}

	@Override
	public int updateAdoptionConsultStatus(AdoptionConsultStatusRequestDTO dto, int userIdx, int consultIdx) {
		Integer checkUserIdx = mapper.checkAdoptionConsultShelter(consultIdx);
		if (checkUserIdx == null) {// 해당 상담신청이 있는지 검증
			return NOT_CONSULT;
		}
		if (checkUserIdx != userIdx) { // 해당 보호시설의 상담 신청이 맞는지 검증
			return NOT_OWNED_CONSULT;
		}
		dto.setIdx(consultIdx);
		int result = mapper.updateAdoptionConsultStatus(dto);
		result = result > 0 ? UPDATE_SUCCESS : ERROR;
		return result;
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
