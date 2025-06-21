package com.animal.api.management.shelter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.management.shelter.mapper.ManagementShelterMapper;
import com.animal.api.management.shelter.model.response.AllManageShelterDTO;

@Service
@Primary
public class ShelterManageServiceImple implements ShelterManageService {

	@Autowired
	private ManagementShelterMapper mapper;

	@Override
	public AllManageShelterDTO getShelterInfo(int userIdx) {
		AllManageShelterDTO dto = mapper.getShelterInfo(userIdx);
		if (dto != null && dto.getDescription() != null) {
			dto.setDescription(dto.getDescription().replaceAll("\n", "<br>"));
		}
		return dto;
	}

}
