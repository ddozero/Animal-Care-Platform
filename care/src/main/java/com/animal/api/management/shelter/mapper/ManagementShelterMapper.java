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

@Mapper
public interface ManagementShelterMapper {

	public AllManageShelterResponseDTO getShelterInfo(int userIdx);

	public int updateSheterInfo(ShelterInfoUpdateRequestDTO dto);

	public List<ManageVolunteerReviewResponseDTO> getVolunteerReview(Map map);

	public List<ManageAdoptionReviewResponseDTO> getAdoptionReview(Map map);

	public int updateTurnVR(ManageVolunteerReplyRequestDTO dto);

	public int addVolunteerReviewApply(ManageVolunteerReplyRequestDTO dto);

	public int updateVolunteerReviewApply(ManageVolunteerReplyRequestDTO dto);

	public int deleteVolunteerReviewApply(ManageVolunteerReplyRequestDTO dto);

	public Integer checkVolunteerReview(@Param("reviewIdx") int reviewIdx);

	public Integer checkShelterUserVR(ManageVolunteerReplyRequestDTO dto);

	public int updateTurnAR(Map map);

	public Integer checkAdoptionReview(@Param("reviewIdx") int reviewIdx);

	public Integer checkShelterUserAR(ManageAdoptionReplyRequestDTO dto);

	public int addAdoptionReviewApply(ManageAdoptionReplyRequestDTO dto);
	
	public int updateAdoptionReviewApply(ManageAdoptionReplyRequestDTO dto);

}
