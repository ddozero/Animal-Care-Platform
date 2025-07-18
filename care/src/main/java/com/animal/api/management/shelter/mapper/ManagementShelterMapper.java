package com.animal.api.management.shelter.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.animal.api.management.shelter.model.request.ManageAdoptionReplyRequestDTO;
import com.animal.api.management.shelter.model.request.ManageVolunteerReplyRequestDTO;
import com.animal.api.management.shelter.model.request.ShelterBoardRequestDTO;
import com.animal.api.management.shelter.model.request.ShelterInfoUpdateRequestDTO;
import com.animal.api.management.shelter.model.response.AllManageShelterResponseDTO;
import com.animal.api.management.shelter.model.response.ManageAdoptionReviewResponseDTO;
import com.animal.api.management.shelter.model.response.ManageVolunteerReviewResponseDTO;
import com.animal.api.management.shelter.model.response.ShelterBoardResponseDTO;

@Mapper
public interface ManagementShelterMapper {

	// 보호시설 기본정보
	public AllManageShelterResponseDTO getShelterInfo(int userIdx);

	public int updateSheterInfo(ShelterInfoUpdateRequestDTO dto);

	// 보호시설 리뷰
	public List<ManageVolunteerReviewResponseDTO> getVolunteerReview(Map map);
	
	public int getVolunteerReviewTotalCnt(@Param("idx") int userIdx); //봉사 리뷰 페이징 구현 total count

	public List<ManageAdoptionReviewResponseDTO> getAdoptionReview(Map map);
	
	public int getAdoptionReviewTotalCnt(@Param("idx") int userIdx); //입양 리뷰 페이징 구현 total count

	public int updateTurnVR(Map map); // VolunteerReview 순번
	
	public ManageVolunteerReviewResponseDTO getReviewIdxVR(@Param("reviewIdx") int reviewIdx); //봉사리뷰조회
	
	public int getMaxTurnVR(@Param("ref") int ref); //봉사 리뷰 turn

	public int addVolunteerReviewApply(ManageVolunteerReplyRequestDTO dto);

	public int updateVolunteerReviewApply(ManageVolunteerReplyRequestDTO dto);

	public int deleteVolunteerReviewApply(ManageVolunteerReplyRequestDTO dto);

	public Integer checkVolunteerReview(@Param("reviewIdx") int reviewIdx);

	public Integer checkShelterUserVR(ManageVolunteerReplyRequestDTO dto);

	public int updateTurnAR(Map map); // AdoptionReview 순번
	
	public ManageAdoptionReviewResponseDTO getReviewIdxAR(@Param("reviewIdx") int reviewIdx); //입양리뷰조회
	
	public int getMaxTurnAR(@Param("ref") int ref); //입양 리뷰 turn

	public Integer checkAdoptionReview(@Param("reviewIdx") int reviewIdx);

	public Integer checkShelterUserAR(ManageAdoptionReplyRequestDTO dto);

	public int addAdoptionReviewApply(ManageAdoptionReplyRequestDTO dto);

	public int updateAdoptionReviewApply(ManageAdoptionReplyRequestDTO dto);

	public int deleteAdoptionReviewApply(ManageAdoptionReplyRequestDTO dto);

	// 보호시설 게시판
	public List<ShelterBoardResponseDTO> getShelterBoardList(Map map);
	
	public int getShelterBoardTotalCnt(@Param("userIdx") int userIdx); //게시판 페이징 구현 total count

	public ShelterBoardResponseDTO getShelterBoardDetail(@Param("idx") int idx, @Param("userIdx") int userIdx);

	public int updateBoardViews(int idx);

	public int getMaxRef(); // 게시판 ref값 설정

	public int addShelterBoard(@Param("dto") ShelterBoardRequestDTO dto, @Param("userIdx") int userIdx);

	public int checkShelterBoard(int idx);

	public Integer checkWriter(ShelterBoardRequestDTO dto);

	public int updateShelterBoard(ShelterBoardRequestDTO dto);

	public int deleteShelterBoard(ShelterBoardRequestDTO dto);

}
