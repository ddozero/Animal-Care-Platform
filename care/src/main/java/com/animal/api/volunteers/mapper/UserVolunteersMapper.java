package com.animal.api.volunteers.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

import com.animal.api.volunteers.model.request.SearchVolunteerRequestDTO;
import com.animal.api.volunteers.model.response.AllVolunteersResponseDTO;
import com.animal.api.volunteers.model.response.VolunteersSubmitResponseDTO;

@Mapper
public interface UserVolunteersMapper {

	public List<AllVolunteersResponseDTO> getAllVolunteers(Map map);

	public AllVolunteersResponseDTO getVolunteersDetail(int idx);

	public List<AllVolunteersResponseDTO> searchVolunteers(SearchVolunteerRequestDTO dto);
	
	public String getVolunteerStatus(int idx);
	
	public int submitVolunteers(VolunteersSubmitResponseDTO dto);

}
