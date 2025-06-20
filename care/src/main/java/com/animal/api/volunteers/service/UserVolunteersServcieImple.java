package com.animal.api.volunteers.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.volunteers.mapper.UserVolunteersMapper;
import com.animal.api.volunteers.model.request.SearchVolunteerRequestDTO;
import com.animal.api.volunteers.model.response.AllVolunteersResponseDTO;

@Service
@Primary
public class UserVolunteersServcieImple implements UserVolunteersService {

	@Autowired
	private UserVolunteersMapper mapper;

	@Override
	public List<AllVolunteersResponseDTO> getAllVolunteers(int listSize, int cp) {
		Map<String, Integer> map = new HashMap<String, Integer>();

		map.put("listSize", listSize);
		map.put("cp", cp);

		List<AllVolunteersResponseDTO> volunteerLists = mapper.getAllVolunteers(map);
		return volunteerLists;
	}

	@Override
	public AllVolunteersResponseDTO getVolunteersDetail(int idx) {
		AllVolunteersResponseDTO dto = mapper.getVolunteersDetail(idx);
		if (dto != null && dto.getContent() != null) {
			dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
		}
		return dto;
	}

	@Override
	public List<AllVolunteersResponseDTO> searchVolunteers(int listSize, int cp, String location, String status,
			String shelterName, String shelterType, Timestamp volunteerDate, String type, int time) {

		SearchVolunteerRequestDTO dto = new SearchVolunteerRequestDTO(cp, listSize, location, status, shelterName,
				shelterType, volunteerDate, type, time);
		List<AllVolunteersResponseDTO> searchVolunteersList = mapper.searchVolunteers(dto);

		return searchVolunteersList;
	}

}
