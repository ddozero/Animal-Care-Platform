package com.animal.api.volunteers.service;

import java.util.List;

import com.animal.api.volunteers.model.response.VolunteersListResponseDTO;

public interface UserVolunteersService {

	public List<VolunteersListResponseDTO> getAllVolunteers(int listSize, int cp);

	public VolunteersListResponseDTO getVolunteersDetail(int idx);

}
