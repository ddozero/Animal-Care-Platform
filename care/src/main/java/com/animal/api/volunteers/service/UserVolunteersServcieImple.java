package com.animal.api.volunteers.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.volunteers.mapper.UserVolunteersMapper;
import com.animal.api.volunteers.model.response.VolunteersListResponseDTO;

@Service
@Primary
public class UserVolunteersServcieImple implements UserVolunteersService {

	@Autowired
	private UserVolunteersMapper mapper;

	@Override
	public List<VolunteersListResponseDTO> getAllVolunteers(int listSize, int cp) {
		Map<String, Integer> map = new HashMap<String, Integer>();

		map.put("listSize", listSize);
		map.put("cp", cp);

		List<VolunteersListResponseDTO> volunteerLists = mapper.getAllVolunteers(map);
		return volunteerLists;
	}

	@Override
	public VolunteersListResponseDTO getVolunteersDetail(int idx) {
		VolunteersListResponseDTO dto = mapper.getVolunteersDetail(idx);
		if (dto != null && dto.getContent() != null) {
			dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
		}
		return dto;
	}

}
