package com.animal.api.management.shelter.service;

import java.util.List;

import com.animal.api.management.shelter.model.request.ManageAdoptionReplyRequestDTO;
import com.animal.api.management.shelter.model.request.ManageVolunteerReplyRequestDTO;
import com.animal.api.management.shelter.model.request.ShelterBoardRequestDTO;
import com.animal.api.management.shelter.model.request.ShelterInfoUpdateRequestDTO;
import com.animal.api.management.shelter.model.response.AllManageShelterResponseDTO;
import com.animal.api.management.shelter.model.response.ManageAdoptionReviewResponseDTO;
import com.animal.api.management.shelter.model.response.ManageVolunteerReviewResponseDTO;
import com.animal.api.management.shelter.model.response.ShelterBoardResponseDTO;

public interface ShelterManageService {

	public static int NOT_SHELTER_MANAGER = -3;
	public static int REPLY_ERROR = -2;
	public static int NOT_REVIEW = -1;
	public static int UPDATE_OK = 0;
	public static int DELETE_OK = 1;
	
	public static int WRITE_OK = 2;
	public static int WRITE_ERROR = -4;
	
	public AllManageShelterResponseDTO getShelterInfo(int idx);

	public int updateSheterInfo(ShelterInfoUpdateRequestDTO dto);

	public List<ManageVolunteerReviewResponseDTO> getVolunteerReview(int listSize, int cp, int idx);

	public List<ManageAdoptionReviewResponseDTO> getAdoptionReview(int listSize, int cp, int idx);

	public int addVolunteerReviewApply(ManageVolunteerReplyRequestDTO dto, int userIdx, int reviewIdx);
	
	public int updateVolunteerReviewApply(ManageVolunteerReplyRequestDTO dto, int userIdx, int reviewIdx);
	
	public int deleteVolunteerReviewApply(int userIdx, int reviewIdx);
	
	public int addAdoptionReviewApply(ManageAdoptionReplyRequestDTO dto, int userIdx, int reviewIdx);
	
	public int updateAdoptionReviewApply(ManageAdoptionReplyRequestDTO dto, int userIdx, int reviewIdx);
	
	public int deleteAdoptionReviewApply(int userIdx, int reviewIdx);
	
	public List<ShelterBoardResponseDTO> getShelterBoardList(int userIdx, int listSize, int cp);
	
	public ShelterBoardResponseDTO getShelterBoardDetail(int idx, int userIdx);
	
	public int addBoardViewCount(int idx);
	
	public int addShelterBoard(ShelterBoardRequestDTO dto);

}
