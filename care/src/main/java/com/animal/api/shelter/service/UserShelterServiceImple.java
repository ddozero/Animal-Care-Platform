package com.animal.api.shelter.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.common.model.PageInformationDTO;
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

	private int listSize = 15;
	private int pageSize = 5;

	@Override
	public List<AllShelterListResponseDTO> getAllShelters(int cp) {
		cp = changeCurrentPage(cp, 15);

		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("listSize", 15);
		map.put("cp", cp);

		List<AllShelterListResponseDTO> shelterList = mapper.getAllShelters(map);
		if (shelterList != null) {
			for (AllShelterListResponseDTO dto : shelterList) {
				List<String> imagePaths = fileManager.getImagePath("shelters", dto.getIdx());
				if (imagePaths != null || imagePaths.size() != 0) {
					dto.setImagePath(imagePaths.get(0));
				}
			}
		}
		return shelterList;
	}

	@Override
	public PageInformationDTO getAllSheltersPageInfo(int cp) {
		if (cp == 0) {
			cp = 1;
		}
		int totalCnt = mapper.getAllSheltersTotalCnt();
		PageInformationDTO pageInfo = new PageInformationDTO(totalCnt, 15, pageSize, cp);
		return pageInfo;
	}

	@Override
	public List<AllShelterListResponseDTO> searchShelters(int cp, String shelterName, String shelterAddress,
			String shelterType) {
		cp = changeCurrentPage(cp, 15);
		SearchShelterRequestDTO request = new SearchShelterRequestDTO(15, cp, shelterName, shelterAddress,
				shelterType);

		List<AllShelterListResponseDTO> shelterList = mapper.searchShelters(request);
		if (shelterList != null) {
			for (AllShelterListResponseDTO dto : shelterList) {
				List<String> imagePaths = fileManager.getImagePath("shelters", dto.getIdx());
				if (imagePaths != null || imagePaths.size() != 0) {
					dto.setImagePath(imagePaths.get(0));
				}
			}
		}
		return shelterList;
	}

	@Override
	public PageInformationDTO searchSheltersPageInfo(int cp, String shelterName, String shelterAddress,
			String shelterType) {
		if (cp == 0) {
			cp = 1;
		}
		SearchShelterRequestDTO request = new SearchShelterRequestDTO(15, cp, shelterName, shelterAddress,
				shelterType);
		int totalCnt = mapper.searchSheltersTotalCnt(request);
		PageInformationDTO pageInfo = new PageInformationDTO(totalCnt, 15, pageSize, cp);
		return pageInfo;
	}

	@Override
	public ShelterDetailResponseDTO getShelterDetail(int idx) {
		ShelterDetailResponseDTO dto = mapper.getShelterDetail(idx);
		if (dto != null) {
			List<String> imagePaths = fileManager.getImagePath("shelters", dto.getIdx());
			if (imagePaths != null || imagePaths.size() != 0) {
				dto.setImagePath(imagePaths.get(0));
			}
		}
		return dto;
	}

	@Override
	public List<ShelterVolunteersResponseDTO> getShelterVolunteers(int cp, int idx) {
		cp = changeCurrentPage(cp, listSize);

		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("listSize", listSize);
		map.put("cp", cp);
		map.put("idx", idx);

		List<ShelterVolunteersResponseDTO> volunteerList = mapper.getShelterVolunteers(map);

		return volunteerList;
	}

	@Override
	public PageInformationDTO getShelterVolunteersPageInfo(int cp, int idx) {
		if (cp == 0) {
			cp = 1;
		}
		int totalCnt = mapper.getShelterVolunteersTotalCnt(idx);
		PageInformationDTO pageInfo = new PageInformationDTO(totalCnt, listSize, pageSize, cp);
		return pageInfo;
	}

	@Override
	public List<ShelterAnimalsResponseDTO> getAllShelterAnimals(int cp, int idx) {
		cp = changeCurrentPage(cp, listSize);

		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("listSize", listSize);
		map.put("cp", cp);
		map.put("idx", idx);

		List<ShelterAnimalsResponseDTO> animalList = mapper.getAllShelterAnimals(map);
		if (animalList != null) {
			for (ShelterAnimalsResponseDTO dto : animalList) {
				List<String> imagePaths = fileManager.getImagePath("animals", dto.getIdx());
				if (imagePaths != null || imagePaths.size() != 0) {
					dto.setImagePath(imagePaths.get(0));
				}
			}
		}
		return animalList;
	}

	@Override
	public PageInformationDTO getAllShelterAnimalsPageInfo(int cp, int idx) {
		if (cp == 0) {
			cp = 1;
		}
		int totalCnt = mapper.getAllShelterAnimalsTotalCnt(idx);
		PageInformationDTO pageInfo = new PageInformationDTO(totalCnt, listSize, pageSize, cp);
		return pageInfo;
	}

	@Override
	public List<ShelterAnimalsResponseDTO> searchShelterAnimals(int idx, int cp, String type, String breed,
			String gender, int neuter, int age, String adoptionStatus, String personality, int size, String name) {
		cp = changeCurrentPage(cp, listSize);

		SearchShelterAnimalRequestDTO request = new SearchShelterAnimalRequestDTO(idx, listSize, cp, type, breed,
				gender, neuter, age, adoptionStatus, personality, size, name);

		List<ShelterAnimalsResponseDTO> animalList = mapper.searchShelterAnimals(request);
		if (animalList != null) {
			for (ShelterAnimalsResponseDTO dto : animalList) {
				List<String> imagePaths = fileManager.getImagePath("animals", dto.getIdx());
				if (imagePaths != null || imagePaths.size() != 0) {
					dto.setImagePath(imagePaths.get(0));
				}
			}
		}
		return animalList;
	}

	@Override
	public PageInformationDTO searchShelterAnimalsPageInfo(int idx, int cp, String type, String breed, String gender,
			int neuter, int age, String adoptionStatus, String personality, int size, String name) {
		if (cp == 0) {
			cp = 1;
		}
		SearchShelterAnimalRequestDTO request = new SearchShelterAnimalRequestDTO(idx, listSize, cp, type, breed,
				gender, neuter, age, adoptionStatus, personality, size, name);

		int totalCnt = mapper.searchShelterAnimalsTotalCnt(request);
		PageInformationDTO pageInfo = new PageInformationDTO(totalCnt, listSize, pageSize, cp);
		return pageInfo;
	}

	@Override
	public List<ShelterBoardListResponseDTO> getShelterBoards(int cp, int idx) {
		cp = changeCurrentPage(cp, listSize);

		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("listSize", listSize);
		map.put("cp", cp);
		map.put("idx", idx);

		List<ShelterBoardListResponseDTO> boardList = mapper.getShelterBoards(map);

		return boardList;
	}

	@Override
	public PageInformationDTO getShelterBoardsPageInfo(int cp, int idx) {
		if (cp == 0) {
			cp = 1;
		}
		int totalCnt = mapper.getShelterBoardsTotalCnt(idx);
		PageInformationDTO pageInfo = new PageInformationDTO(totalCnt, listSize, pageSize, cp);
		return pageInfo;
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
	public List<ShelterVolunteerReviewResponseDTO> getShelterVolunteerReviews(int cp, int idx) {
		cp = changeCurrentPage(cp, listSize);

		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("listSize", listSize);
		map.put("cp", cp);
		map.put("idx", idx);

		List<ShelterVolunteerReviewResponseDTO> reviewList = mapper.getShelterVolunteerReviews(map);
		if (reviewList != null) {
			for (ShelterVolunteerReviewResponseDTO dto : reviewList) {
				List<String> imagePaths = fileManager.getImagePath("volunteerReviews", dto.getIdx());
				if (imagePaths != null || imagePaths.size() != 0) {
					dto.setImagePath(imagePaths.get(0));
				}
			}
		}
		return reviewList;
	}
	
	@Override
	public PageInformationDTO getShelterVolunteerReviewsPageInfo(int cp, int idx) {
		if (cp == 0) {
			cp = 1;
		}
		int totalCnt = mapper.getShelterVolunteerReviewsTotalCnt(idx);
		PageInformationDTO pageInfo = new PageInformationDTO(totalCnt, listSize, pageSize, cp);
		return pageInfo;
	}

	@Override
	public List<ShelterAdoptionReviewResponseDTO> getShelterAdoptionReviews(int cp, int idx) {
		cp = changeCurrentPage(cp, listSize);

		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("listSize", listSize);
		map.put("cp", cp);
		map.put("idx", idx);

		List<ShelterAdoptionReviewResponseDTO> reviewList = mapper.getShelterAdoptionReviews(map);
		if (reviewList != null) {
			for (ShelterAdoptionReviewResponseDTO dto : reviewList) {
				List<String> imagePaths = fileManager.getImagePath("adoptionReviews", dto.getIdx());
				if (imagePaths != null || imagePaths.size() != 0) {
					dto.setImagePath(imagePaths.get(0));
				}
			}
		}
		return reviewList;
	}
	
	@Override
	public PageInformationDTO getShelterAdoptionReviewsPageInfo(int cp, int idx) {
		if (cp == 0) {
			cp = 1;
		}
		int totalCnt = mapper.getShelterAdoptionReviewsTotalCnt(idx);
		PageInformationDTO pageInfo = new PageInformationDTO(totalCnt, listSize, pageSize, cp);
		return pageInfo;
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
