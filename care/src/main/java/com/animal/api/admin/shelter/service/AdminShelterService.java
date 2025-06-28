package com.animal.api.admin.shelter.service;

import java.util.List;

import com.animal.api.admin.shelter.model.response.ShelterJoinRequestListResponseDTO;
import com.animal.api.common.model.PageInformationDTO;

public interface AdminShelterService {

	public static int UPDATE_SUCCESS = 1;
	public static int NOT_REQUEST = 2;
	public static int APPROVED = 3;
	public static int WITHDRAWN = 4;
	public static int NOT_ERROR = 5;
	public static int DELETE_SUCCESS = 6;
	public static int NOT_FOUND_VOLUNTEER = 7;
	public static int NOT_OWNED_VOLUNTEER = 8;
	public static int ERROR = -1;

	public int deleteVolunteer(int volunteerIdx, int shelterIdx);

	public List<ShelterJoinRequestListResponseDTO> getShelterJoinRequestList(int cp);

	public PageInformationDTO getShelterJoinRequestListPageInfo(int cp);

	public ShelterJoinRequestListResponseDTO getShelterJoinRequestDetail(int idx);

	public int updateShelterJoinRequestStatus(int idx);

	public int ShelterJoinRejection(int idx);
}
