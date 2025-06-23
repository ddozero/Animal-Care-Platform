package com.animal.api.shelter.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.common.util.FileManager;
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
	@Autowired
	private FileManager fileManager;

	@Override
	public List<AllShelterListResponseDTO> getAllShelters(int listSize, int cp) {
		cp = changeCurrentPage(cp, listSize);

		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("listSize", listSize);
		map.put("cp", cp);

		List<AllShelterListResponseDTO> shelterList = mapper.getAllShelters(map);
		if (shelterList != null) {
			for (AllShelterListResponseDTO dto : shelterList) {
				dto.setImagePath(fileManager.getImagePath("shelters", dto.getIdx()).get(0));
			}
		}
		return shelterList;
	}

	@Override
	public List<AllShelterListResponseDTO> searchShelters(int listSize, int cp, String shelterName,
			String shelterAddress, String shelterType) {
		cp = changeCurrentPage(cp, listSize);
		SearchShelterRequestDTO request = new SearchShelterRequestDTO(listSize, cp, shelterName, shelterAddress,
				shelterType);

		List<AllShelterListResponseDTO> shelterList = mapper.searchShelters(request);
		if (shelterList != null) {
			for (AllShelterListResponseDTO dto : shelterList) {
				dto.setImagePath(fileManager.getImagePath("shelters", dto.getIdx()).get(0));
			}
		}
		return shelterList;
	}

	@Override
	public ShelterDetailResponseDTO getShelterDetail(int idx) {
		ShelterDetailResponseDTO dto = mapper.getShelterDetail(idx);
		if (dto != null) {
			dto.setImagePath(fileManager.getImagePath("shelters", dto.getIdx()).get(0));
		}
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
		if (animalList != null) {
			for (ShelterAnimalsResponseDTO dto : animalList) {
				dto.setImagePath(fileManager.getImagePath("animals", dto.getIdx()).get(0));
			}
		}
		return animalList;
	}

	@Override
	public List<ShelterAnimalsResponseDTO> searchShelterAnimals(int idx, int listSize, int cp, String type,
			String breed, String gender, int neuter, int age, String adoptionStatus, String personality, int size,
			String name) {
		cp = changeCurrentPage(cp, listSize);

		SearchShelterAnimalRequestDTO request = new SearchShelterAnimalRequestDTO(idx, listSize, cp, type, breed,
				gender, neuter, age, adoptionStatus, personality, size, name);

		List<ShelterAnimalsResponseDTO> animalList = mapper.searchShelterAnimals(request);
		if (animalList != null) {
			for (ShelterAnimalsResponseDTO dto : animalList) {
				dto.setImagePath(fileManager.getImagePath("animals", dto.getIdx()).get(0));
			}
		}
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
			if (dto != null) {
				dto.setImagePaths(fileManager.getImagePath("boards", idx));
				dto.setFilePaths(fileManager.getFilePath("boards", idx));
			}
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
		if (reviewList != null) {
			for (ShelterVolunteerReviewResponseDTO dto : reviewList) {
				dto.setImagePath(fileManager.getImagePath("volunteerReviews", dto.getIdx()).get(0));
			}
		}
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
		if (reviewList != null) {
			for (ShelterAdoptionReviewResponseDTO dto : reviewList) {
				dto.setImagePath(fileManager.getImagePath("adoptionReviews", dto.getIdx()).get(0));
			}
		}
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
