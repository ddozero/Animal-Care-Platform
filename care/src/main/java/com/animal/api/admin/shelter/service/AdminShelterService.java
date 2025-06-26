package com.animal.api.admin.shelter.service;

import java.util.List;

import com.animal.api.admin.shelter.model.response.ShelterJoinRequestListResponseDTO;

public interface AdminShelterService {

	public List<ShelterJoinRequestListResponseDTO> getShelterJoinRequestList(int listSize, int cp);
}
