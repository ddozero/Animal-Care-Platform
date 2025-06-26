package com.animal.api.admin.shelter.service;

import java.util.List;

import com.animal.api.admin.shelter.model.response.ShelterJoinRequestListResponseDTO;

public interface AdminShelterService {

	static int UPDATE_SUCCESS = 1;
	static int NOT_REQUEST = 2;
	static int APPROVED = 3;
	static int WITHDRAWN = 4;
	static int NOT_ERROR = 5;
	static int ERROR = -1;

	public List<ShelterJoinRequestListResponseDTO> getShelterJoinRequestList(int listSize, int cp);

	public ShelterJoinRequestListResponseDTO getShelterJoinRequestDetail(int idx);

	public int updateShelterJoinRequestStatus(int idx);

	public int ShelterJoinRejection(int idx);
}
