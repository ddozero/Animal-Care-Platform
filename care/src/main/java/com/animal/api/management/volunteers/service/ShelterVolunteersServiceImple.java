package com.animal.api.management.volunteers.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.animal.api.common.util.FileManager;
import com.animal.api.management.volunteers.mapper.ShelterVolunteersMappper;
import com.animal.api.management.volunteers.model.request.ShelterVolunteerUpdateRequestDTO;
import com.animal.api.management.volunteers.model.request.ShelterVolunteersInsertDTO;
import com.animal.api.management.volunteers.model.response.CountApprovedResponseDTO;
import com.animal.api.management.volunteers.model.response.CountPeopleResponseDTO;
import com.animal.api.management.volunteers.model.response.ShelterVolunteerApplicationDetailResponseDTO;
import com.animal.api.management.volunteers.model.response.ShelterVolunteerApplicationsResponseDTO;
import com.animal.api.management.volunteers.model.response.ShelterVolunteerDetailResponseDTO;
import com.animal.api.management.volunteers.model.response.ShelterVolunteersListResponseDTO;

@Service
@Primary
public class ShelterVolunteersServiceImple implements ShelterVolunteersService {

	@Autowired
	private ShelterVolunteersMappper mapper;

	@Autowired
	private FileManager fileManager;

	@Override
	public List<ShelterVolunteersListResponseDTO> getShelterAllVolunteers(int userIdx, int listSize, int cp) {
		if (cp == 0) {
			cp = 1;
		}
		cp = (cp - 1) * listSize;

		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("userIdx", userIdx);
		map.put("listSize", listSize);
		map.put("cp", cp);

		List<ShelterVolunteersListResponseDTO> shelterVolunteersList = mapper.getShelterAllVolunteers(map);

		return shelterVolunteersList;
	}

	@Override
	public int addShelterVolunteer(ShelterVolunteersInsertDTO dto) {
		int result = mapper.addShelterVolunteer(dto);

		if (result == 1) {
			return POST_SUCCESS;
		} else {
			return ERROR;
		}
	}

	@Override
	public int uploadShelterVolunteerImage(MultipartFile[] files, int idx) {
		boolean result = fileManager.uploadImages("volunteers", idx, files);
		if (result) {
			return UPLOAD_SUCCESS;
		} else {
			mapper.deleteShelterVolunteer(idx);
			return UPLOAD_FAIL;
		}
	}

	@Override
	public ShelterVolunteerDetailResponseDTO getShelterVolunteerDetail(int volunteerIdx) {
		ShelterVolunteerDetailResponseDTO volunteerDetail = mapper.getShelterVolunteerDetail(volunteerIdx);
		if (volunteerDetail != null) {
			List<String> imagePaths = fileManager.getImagePath("volunteers", volunteerDetail.getIdx());
			if (imagePaths != null || imagePaths.size() != 0) {
				volunteerDetail.setImagePath(imagePaths.get(0));
			}
		}
		return volunteerDetail;
	}

	@Override
	public int updateShelterVolunteer(ShelterVolunteerUpdateRequestDTO dto, int volunteerIdx) {
		Integer userIdx = mapper.checkMyVolunteer(volunteerIdx);
		if (userIdx == null || userIdx == 0) {// 봉사 존재 여부 검증
			return VOLUNTEER_NOT_FOUND;
		} else if (userIdx != dto.getUserIdx()) {// 로그인한 보호시설이 등록한 봉사인지 검증
			return NOT_OWNED_VOLUNTEER;
		}

		int result = mapper.updateShelterVolunteer(dto);

		if (result == 1) {
			return UPDATE_SUCCESS;
		} else {
			return ERROR;
		}
	}

	@Override
	public int deleteShelterVolunteer(int volunteerIdx, int userIdx) {
		Integer checkUserIdx = mapper.checkMyVolunteer(volunteerIdx);
		if (checkUserIdx == null || checkUserIdx == 0) {// 봉사 존재 여부 검증
			return VOLUNTEER_NOT_FOUND;
		} else if (checkUserIdx != userIdx) {// 로그인한 보호시설이 등록한 봉사인지 검증
			return NOT_OWNED_VOLUNTEER;
		}
		int result = mapper.deleteShelterVolunteer(volunteerIdx);

		if (result == 1) {
			fileManager.deleteFolder("volunteers", volunteerIdx);
			return DELETE_SUCCESS;
		} else {
			return ERROR;
		}
	}

	@Override
	public List<ShelterVolunteerApplicationsResponseDTO> getShelterVolunteerApplications(int volunteerIdx, int listSize,
			int cp) {
		if (cp == 0) {
			cp = 1;
		}
		cp = (cp - 1) * listSize;

		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("volunteerIdx", volunteerIdx);
		map.put("listSize", listSize);
		map.put("cp", cp);

		List<ShelterVolunteerApplicationsResponseDTO> shelterVolunteerApplications = mapper
				.getShelterVolunteerApplications(map);

		return shelterVolunteerApplications;
	}

	@Override
	public ShelterVolunteerApplicationDetailResponseDTO getShelterVolunteerApplicationDetail(int applicationIdx) {
		ShelterVolunteerApplicationDetailResponseDTO shelterVolunteerApplicationDetail = mapper
				.getShelterVolunteerApplicationDetail(applicationIdx);
		return shelterVolunteerApplicationDetail;
	}

	@Override
	public int approveShelterVolunteerApplication(int volunteerIdx, int applicationIdx, int userIdx) {
		Integer checkUserIdx = mapper.checkMyVolunteer(volunteerIdx);
		if (checkUserIdx == null || checkUserIdx == 0) {// 봉사 존재 여부 검증
			return VOLUNTEER_NOT_FOUND;
		} else if (checkUserIdx != userIdx) {// 로그인한 보호시설이 등록한 봉사인지 검증
			return NOT_OWNED_VOLUNTEER;
		}

		List<CountApprovedResponseDTO> countApprovedList = mapper.countApproved(volunteerIdx);// 승인된 봉사 인원 조회(승인완료만)
		int totalApprovedPeople = 0;
		for (CountApprovedResponseDTO dto : countApprovedList) {
			int male = dto.getMale();
			int female = dto.getFemale();
			totalApprovedPeople += male + female;
		}

		int capacity = mapper.checkCapacity(volunteerIdx);// 봉사 모집인원 조회

		CountPeopleResponseDTO applicationPeople = mapper.getApplicationPeople(applicationIdx);// 신청 인원 조회(신청완료,거절 포함)
		int totalapplicationPeople = 0;
		int male = applicationPeople.getMale();
		int female = applicationPeople.getFemale();
		totalapplicationPeople += (male + female);

		if (totalApprovedPeople + totalapplicationPeople > capacity) {// 신청한 인원과 승인된 인원이 모집인원을 초과했는지 확인 메서드
			return EXCEEDS_CAPACITY;
		} else {
			int result = mapper.approveShelterVolunteerApplication(applicationIdx);
			if (result == 1) {
				return UPDATE_SUCCESS;
			}
			return ERROR;
		}
	}

	@Override
	public int cancelShelterVolunteerApplication(int volunteerIdx, int applicationIdx, int userIdx) {
		Integer checkUserIdx = mapper.checkMyVolunteer(volunteerIdx);
		if (checkUserIdx == null || checkUserIdx == 0) {// 봉사 존재 여부 검증
			return VOLUNTEER_NOT_FOUND;
		} else if (checkUserIdx != userIdx) {// 로그인한 보호시설이 등록한 봉사인지 검증
			return NOT_OWNED_VOLUNTEER;
		}

		int result = mapper.cancelShelterVolunteerApplication(applicationIdx);

		if (result == 1) {
			return UPDATE_SUCCESS;
		} else {
			return ERROR;
		}
	}
}
