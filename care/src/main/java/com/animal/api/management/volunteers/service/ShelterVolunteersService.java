package com.animal.api.management.volunteers.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.animal.api.management.volunteers.model.request.ShelterVolunteerUpdateRequestDTO;
import com.animal.api.management.volunteers.model.request.ShelterVolunteersInsertDTO;
import com.animal.api.management.volunteers.model.response.ShelterVolunteerApplicationDetailResponseDTO;
import com.animal.api.management.volunteers.model.response.ShelterVolunteerApplicationsResponseDTO;
import com.animal.api.management.volunteers.model.response.ShelterVolunteerDetailResponseDTO;
import com.animal.api.management.volunteers.model.response.ShelterVolunteersListResponseDTO;

public interface ShelterVolunteersService {
	static int POST_SUCCESS = 1;
	static int UPLOAD_SUCCESS = 2;
	static int UPLOAD_FAIL = 3;
	static int UPDATE_SUCCESS = 4;
	static int UPDATE_FAIL = 5;
	static int VOLUNTEER_NOT_FOUND = 6;
	static int NOT_OWNED_VOLUNTEER = 7;
	static int DELETE_SUCCESS = 8;
	static int EXCEEDS_CAPACITY = 9;
	static int ERROR = -1;

	public List<ShelterVolunteersListResponseDTO> getShelterAllVolunteers(int userIdx, int listSize, int cp);

	public int addShelterVolunteer(ShelterVolunteersInsertDTO dto);

	public int uploadShelterVolunteerImage(MultipartFile[] files, int idx);

	public ShelterVolunteerDetailResponseDTO getShelterVolunteerDetail(int volunteerIdx);

	public int updateShelterVolunteer(ShelterVolunteerUpdateRequestDTO dto, int volunteerIdx);

	public int deleteShelterVolunteer(int volunteerIdx, int userIdx);

	public List<ShelterVolunteerApplicationsResponseDTO> getShelterVolunteerApplications(int volunteerIdx, int listSize,
			int cp);

	public ShelterVolunteerApplicationDetailResponseDTO getShelterVolunteerApplicationDetail(int applicationIdx);

	public int approveShelterVolunteerApplication(int volunteerIdx, int applicationIdx, int userIdx);
}
