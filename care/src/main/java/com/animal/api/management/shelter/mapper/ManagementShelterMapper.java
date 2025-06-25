package com.animal.api.management.shelter.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.animal.api.management.shelter.model.request.ManageAdoptionReplyRequestDTO;
import com.animal.api.management.shelter.model.request.ManageVolunteerReplyRequestDTO;
import com.animal.api.management.shelter.model.request.ShelterInfoUpdateRequestDTO;
import com.animal.api.management.shelter.model.response.AllManageShelterResponseDTO;
import com.animal.api.management.shelter.model.response.ManageAdoptionReviewResponseDTO;
import com.animal.api.management.shelter.model.response.ManageVolunteerReviewResponseDTO;
import com.animal.api.management.shelter.model.response.ShelterBoardResponseDTO;

@Mapper
public interface ManagementShelterMapper {
	
	//보호시설 기본정보
	public AllManageShelterResponseDTO getShelterInfo(int userIdx);

	public int updateSheterInfo(ShelterInfoUpdateRequestDTO dto);
	
	//보호시설 리뷰
	public List<ManageVolunteerReviewResponseDTO> getVolunteerReview(Map map);

	public List<ManageAdoptionReviewResponseDTO> getAdoptionReview(Map map);

	public int updateTurnVR(ManageVolunteerReplyRequestDTO dto); //VolunteerReview 순번

	public int addVolunteerReviewApply(ManageVolunteerReplyRequestDTO dto);

	public int updateVolunteerReviewApply(ManageVolunteerReplyRequestDTO dto);

	public int deleteVolunteerReviewApply(ManageVolunteerReplyRequestDTO dto);

	public Integer checkVolunteerReview(@Param("reviewIdx") int reviewIdx);

	public Integer checkShelterUserVR(ManageVolunteerReplyRequestDTO dto);

	public int updateTurnAR(Map map); //AdoptionReview 순번

	public Integer checkAdoptionReview(@Param("reviewIdx") int reviewIdx);

	public Integer checkShelterUserAR(ManageAdoptionReplyRequestDTO dto);

	public int addAdoptionReviewApply(ManageAdoptionReplyRequestDTO dto);
	
	public int updateAdoptionReviewApply(ManageAdoptionReplyRequestDTO dto);
	
	public int deleteAdoptionReviewApply(ManageAdoptionReplyRequestDTO dto);
	
	//보호시설 게시판
	public List<ShelterBoardResponseDTO> getShelterBoardList(Map map);
	
	public ShelterBoardResponseDTO getShelterBoardDetail(@Param("idx")int idx, @Param("userIdx")int userIdx);
	
	public int updateBoardViews(int idx);

}
