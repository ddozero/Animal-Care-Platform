package com.animal.api.management.volunteers.service;

import java.util.List;

import com.animal.api.management.volunteers.model.request.ShelterVolunteersInsertDTO;
import com.animal.api.management.volunteers.model.response.ShelterVolunteersListResponseDTO;

public interface ShelterVolunteersService {
	static int POST_SUCCESS = 1;
	static int ERROR = -1;

	public List<ShelterVolunteersListResponseDTO> getShelterAllVolunteers(int userIdx, int listSize, int cp);

	public int addShelterVolunteer(ShelterVolunteersInsertDTO dto);
}
