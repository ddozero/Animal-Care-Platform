package com.animal.api.volunteers.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.animal.api.support.model.request.SearchNoticeRequestDTO;
import com.animal.api.volunteers.model.request.SearchVolunteerRequestDTO;
import com.animal.api.volunteers.model.request.VolunteersSubmitRequestDTO;
import com.animal.api.volunteers.model.response.AllVolunteersResponseDTO;

@Mapper
public interface UserVolunteersMapper {

	public List<AllVolunteersResponseDTO> getAllVolunteers(Map map);
	
	public int getAllVolunteersTotalCnt(); //페이징 구현을 위한 total count

	public AllVolunteersResponseDTO getVolunteersDetail(int idx);

	public List<AllVolunteersResponseDTO> searchVolunteers(SearchVolunteerRequestDTO dto);

	public int getSearchVolunteerTotalcnt(SearchVolunteerRequestDTO dto); //페이징 구현을 위한 total count
	
	public String getVolunteerStatus(int volunteerIdx);

	public int submitVolunteers(VolunteersSubmitRequestDTO dto);

	public int checkSubmit(@Param("userIdx") int userIdx, @Param("volunteerIdx") int volunteerIdx);
	
	public int updateApplicants(VolunteersSubmitRequestDTO dto);
	
	//봉사 캘린더 조회
	public List<AllVolunteersResponseDTO> getVolunteerCalendar();


}
