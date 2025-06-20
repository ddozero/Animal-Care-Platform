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
import com.animal.api.volunteers.model.response.VolunteersSubmitResponseDTO;

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
	public List<AllVolunteersResponseDTO> searchVolunteers(int listSize, int cp, String title, String content,
			String location, String status, String shelter, String shelterType, Timestamp volunteerDate, String type,
			int time) {

		SearchVolunteerRequestDTO dto = new SearchVolunteerRequestDTO(cp, listSize, title, content, location, status,
				shelter, shelterType, volunteerDate, type, time);
		List<AllVolunteersResponseDTO> searchVolunteersList = mapper.searchVolunteers(dto);

		return searchVolunteersList;
	}

	@Override
	public int submitVolunteers(VolunteersSubmitResponseDTO dto) {
		
		String getVolunteerStatus = mapper.getVolunteerStatus(dto.getIdx());
		int checkStatus = 0;
		
		if (getVolunteerStatus == null) {
			return SUBMIT_ERROR;
		} 
		
		if (checkStatus == 3 || checkStatus == 4) {
			return SUBMIT_NOT_OK;
		}

		int result = mapper.submitVolunteers(dto);
		
		if (result > 0) {
			return SUBMIT_OK;
		}else {
			return SUBMIT_ERROR;
		}
	}

}
