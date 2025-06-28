package com.animal.api.management.volunteers.service;

import java.util.List;

import com.animal.api.management.volunteers.model.response.ShelterVolunteersListResponseDTO;

public interface ShelterVolunteersService {

	public List<ShelterVolunteersListResponseDTO> getShelterAllVolunteers(int userIdx, int listSize, int cp);
}
