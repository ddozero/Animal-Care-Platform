package com.animal.api.management.shelter.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.animal.api.management.shelter.model.request.ShelterInfoUpdateRequestDTO;
import com.animal.api.management.shelter.model.response.AllManageShelterResponseDTO;
import com.animal.api.management.shelter.model.response.ManageVolunteerReviewResponseDTO;

@Mapper
public interface ManagementShelterMapper {

	public AllManageShelterResponseDTO getShelterInfo(int userIdx);

	public int updateSheterInfo(ShelterInfoUpdateRequestDTO dto);

	public List<ManageVolunteerReviewResponseDTO> getVolunteerReview(Map map);

}
