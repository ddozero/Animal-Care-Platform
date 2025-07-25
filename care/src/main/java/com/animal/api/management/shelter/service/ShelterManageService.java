package com.animal.api.management.shelter.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.animal.api.common.model.PageInformationDTO;
import com.animal.api.management.shelter.model.request.ManageAdoptionReplyRequestDTO;
import com.animal.api.management.shelter.model.request.ManageVolunteerReplyRequestDTO;
import com.animal.api.management.shelter.model.request.ShelterBoardRequestDTO;
import com.animal.api.management.shelter.model.request.ShelterInfoUpdateRequestDTO;
import com.animal.api.management.shelter.model.response.AllManageShelterResponseDTO;
import com.animal.api.management.shelter.model.response.ManageAdoptionReviewResponseDTO;
import com.animal.api.management.shelter.model.response.ManageVolunteerReviewResponseDTO;
import com.animal.api.management.shelter.model.response.ShelterBoardResponseDTO;

public interface ShelterManageService {
	
	public static int NOT_ALLOWED_REPLY = -5;
	public static int NOT_EXIST_BOARD = -4;
	public static int NOT_SHELTER_MANAGER = -3;
	public static int NOT_REVIEW = -2;
	public static int ERROR = -1;
	public static int UPDATE_OK = 0;
	public static int DELETE_OK = 1;
	public static int WRITE_OK = 2;
	public static int UPLOAD_OK = 3;
	

	// 보호시설 기본정보
	public AllManageShelterResponseDTO getShelterInfo(int idx);

	public int updateShelterInfo(ShelterInfoUpdateRequestDTO dto, int userIdx);
	
	public int uploadShelterFile(MultipartFile[] files, int idx);

	// 보호시설 리뷰
	public List<ManageVolunteerReviewResponseDTO> getVolunteerReview(int cp, int idx);
	
	public PageInformationDTO getVolunteerReviewPage(int cp, int idx);

	public List<ManageAdoptionReviewResponseDTO> getAdoptionReview(int cp, int idx);
	
	public PageInformationDTO getAdoptionReviewPage(int cp, int idx);

	public int addVolunteerReviewApply(ManageVolunteerReplyRequestDTO dto, int userIdx, int reviewIdx);

	public int updateVolunteerReviewApply(ManageVolunteerReplyRequestDTO dto, int userIdx, int reviewIdx);

	public int deleteVolunteerReviewApply(int userIdx, int reviewIdx);

	public int addAdoptionReviewApply(ManageAdoptionReplyRequestDTO dto, int userIdx, int reviewIdx);

	public int updateAdoptionReviewApply(ManageAdoptionReplyRequestDTO dto, int userIdx, int reviewIdx);

	public int deleteAdoptionReviewApply(int userIdx, int reviewIdx);

	// 보호시설 게시판
	public List<ShelterBoardResponseDTO> getShelterBoardList(int userIdx, int cp);
	
	public PageInformationDTO getShelterBoardTotalCnt(int userIdx, int cp);

	public ShelterBoardResponseDTO getShelterBoardDetail(int idx, int userIdx);

	public int addShelterBoard(ShelterBoardRequestDTO dto, int userIdx);

	public int uploadBoardFile(MultipartFile[] files, int idx);
	
	public int updateShelterBoard(ShelterBoardRequestDTO dto);
	
	public int deleteShelterBoard(ShelterBoardRequestDTO dto, int idx);

}
