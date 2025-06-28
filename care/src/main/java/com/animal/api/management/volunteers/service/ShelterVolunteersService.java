package com.animal.api.management.volunteers.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.animal.api.management.volunteers.model.request.ShelterVolunteersInsertDTO;
import com.animal.api.management.volunteers.model.response.ShelterVolunteersListResponseDTO;

public interface ShelterVolunteersService {
	static int POST_SUCCESS = 1;
	static int UPLOAD_SUCCESS = 2;
	static int UPLOAD_FAIL = 3;
	static int ERROR = -1;

	public List<ShelterVolunteersListResponseDTO> getShelterAllVolunteers(int userIdx, int listSize, int cp);

	public int addShelterVolunteer(ShelterVolunteersInsertDTO dto);

	public int uploadShelterVolunteerImage(MultipartFile[] files, int idx);
}
