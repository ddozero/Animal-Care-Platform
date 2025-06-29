package com.animal.api.volunteers.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.animal.api.volunteers.model.request.VolunteersSubmitRequestDTO;
import com.animal.api.volunteers.model.response.AllVolunteersResponseDTO;
import com.animal.api.volunteers.model.response.SearchVolunteerResponseDTO;

@Mapper
public interface UserVolunteersMapper {

	public List<AllVolunteersResponseDTO> getAllVolunteers(Map map);

	public AllVolunteersResponseDTO getVolunteersDetail(int idx);

	public List<AllVolunteersResponseDTO> searchVolunteers(SearchVolunteerResponseDTO dto);

	public String getVolunteerStatus(int volunteerIdx);

	public int submitVolunteers(VolunteersSubmitRequestDTO dto);

	public int checkSubmit(@Param("userIdx") int userIdx, @Param("volunteerIdx") int volunteerIdx);
	
	public int updateApplicants(VolunteersSubmitRequestDTO dto);

}
