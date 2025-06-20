package com.animal.api.volunteers.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

import com.animal.api.volunteers.model.request.SearchVolunteerRequestDTO;
import com.animal.api.volunteers.model.response.AllVolunteersResponseDTO;

@Mapper
public interface UserVolunteersMapper {

	public List<AllVolunteersResponseDTO> getAllVolunteers(Map map);

	public AllVolunteersResponseDTO getVolunteersDetail(int idx);

	public List<AllVolunteersResponseDTO> searchVolunteers(SearchVolunteerRequestDTO dto);

}
