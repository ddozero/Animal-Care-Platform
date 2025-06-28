package com.animal.api.management.volunteers.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.common.util.FileManager;
import com.animal.api.management.volunteers.mapper.ShelterVolunteersMappper;
import com.animal.api.management.volunteers.model.response.ShelterVolunteersListResponseDTO;

@Service
@Primary
public class ShelterVolunteersServiceImple implements ShelterVolunteersService {

	@Autowired
	private ShelterVolunteersMappper mapper;

	@Autowired
	private FileManager fileManager;

	@Override
	public List<ShelterVolunteersListResponseDTO> getShelterAllVolunteers(int userIdx, int listSize, int cp) {
		if (cp == 0) {
			cp = 1;
		}
		cp = (cp - 1) * listSize;

		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("userIdx", userIdx);
		map.put("listSize", listSize);
		map.put("cp", cp);

		List<ShelterVolunteersListResponseDTO> shelterVolunteersList = mapper.getShelterAllVolunteers(map);

		return shelterVolunteersList;
	}
}
