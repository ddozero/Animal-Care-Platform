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
import com.animal.api.common.model.PageInformationDTO;
import com.animal.api.common.util.FileManager;

@Service
@Primary
public class UserAnimalServiceImple implements UserAnimalService {

	@Autowired
	private UserAnimalMapper mapper;
	@Autowired
	private FileManager fileManager;

	private int listSize = 15;
	private int pageSize = 5;

	@Override
	public List<AllAnimalListResponseDTO> getAllAnimals(int cp) {

		cp = changeCurrentPage(cp, listSize);

		Map<String, Integer> map = new HashMap<String, Integer>();

		map.put("listSize", listSize);
		map.put("cp", cp);

		List<AllAnimalListResponseDTO> animalList = mapper.getAllAnimals(map);

		if (animalList != null) {
			for (AllAnimalListResponseDTO dto : animalList) {
				List<String> imagePaths = fileManager.getImagePath("animals", dto.getIdx());
				if (imagePaths != null || imagePaths.size() != 0) {
					dto.setImagePath(imagePaths.get(0));
				}
			}
		}

		return animalList;
	}

	@Override
	public PageInformationDTO getAllAnimalsPageInfo(int cp) {
		if (cp == 0) {
			cp = 1;
		}
		int totalCnt = mapper.getAllAnimalsTotalCnt();
		PageInformationDTO pageInfo = new PageInformationDTO(totalCnt, listSize, pageSize, cp);
		return pageInfo;
	}

	@Override
	public List<AllAnimalListResponseDTO> searchAnimals(int cp, String type, String breed, String gender, int neuter,
			int age, String adoptionStatus, String personality, int size, String name) {

		cp = changeCurrentPage(cp, listSize);

		SearchConditionsRequestDTO request = new SearchConditionsRequestDTO(listSize, cp, type, breed, gender, neuter,
				age, adoptionStatus, personality, size, name);
		List<AllAnimalListResponseDTO> animalList = mapper.searchAnimals(request);

		if (animalList != null) {
			for (AllAnimalListResponseDTO dto : animalList) {
				List<String> imagePaths = fileManager.getImagePath("animals", dto.getIdx());
				if (imagePaths != null || imagePaths.size() != 0) {
					dto.setImagePath(imagePaths.get(0));
				}
			}
		}

		return animalList;
	}

	@Override
	public PageInformationDTO searchAnimalsPageInfo(int cp, String type, String breed, String gender, int neuter,
			int age, String adoptionStatus, String personality, int size, String name) {
		SearchConditionsRequestDTO request = new SearchConditionsRequestDTO(listSize, cp, type, breed, gender, neuter,
				age, adoptionStatus, personality, size, name);
		if (cp == 0) {
			cp = 1;
		}
		int totalCnt = mapper.searchAnimalsTotalCnt(request);
		PageInformationDTO pageInfo = new PageInformationDTO(totalCnt, listSize, pageSize, cp);

		return pageInfo;
	}

	@Override
	public AnimalDetailResponseDTO getAnimalDetail(int idx) {
		AnimalDetailResponseDTO dto = mapper.getAnimalDetail(idx);
		if (dto != null) {
			List<String> imagePaths = fileManager.getImagePath("animals", dto.getIdx());
			if (imagePaths != null || imagePaths.size() != 0) {
				dto.setImagePath(imagePaths.get(0));
			}
		}
		return dto;
	}

	@Override
	public AdoptionAnimalResponseDTO getAdoptionInfo(int idx) {
		AdoptionAnimalResponseDTO dto = mapper.getAdoptionInfo(idx);
		if (dto != null) {
			List<String> imagePaths = fileManager.getImagePath("animals", dto.getIdx());
			if (imagePaths != null || imagePaths.size() != 0) {
				dto.setImagePath(imagePaths.get(0));
			}
		}
		return dto;
	}

	@Override
	public int submitAdoption(AdoptionSubmitReqestDTO dto, int idx) {
		
		dto.setAnimalIdx(idx);
		// 입양 가능 상태 검사
		Integer checkStatus = mapper.checkAdoptionStatus(dto.getAnimalIdx());
		if (checkStatus == null) {
			return NOT_FOUND_ANIMAL;
		}

		if (checkStatus != 1) {
			return RESERVATION_UNAVAILABLE;
		}
		Map<String, Integer> map = new HashMap<String, Integer>();

		// 중복 신청 검사
		map.put("userIdx", dto.getUserIdx());
		map.put("animalIdx", dto.getAnimalIdx());
		Integer checkDuplicate = mapper.checkDuplicateUser(map);
		if (checkDuplicate != null) {
			return RESERVATION_DUPLICATE;
		}

		int result = mapper.submitAdoption(dto);
		if (result > 0) {
			return RESERVATION_COMPLETED;
		} else {
			return RESERVATION_FAILD;
		}
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
