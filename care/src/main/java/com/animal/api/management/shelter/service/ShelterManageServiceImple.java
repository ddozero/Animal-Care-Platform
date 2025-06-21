package com.animal.api.management.shelter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.management.shelter.mapper.ManagementShelterMapper;
import com.animal.api.management.shelter.model.request.ShelterInfoUpdateRequestDTO;
import com.animal.api.management.shelter.model.response.AllManageShelterResponseDTO;

@Service
@Primary
public class ShelterManageServiceImple implements ShelterManageService {

	@Autowired
	private ManagementShelterMapper mapper;

	@Override
	public AllManageShelterResponseDTO getShelterInfo(int idx) {
		AllManageShelterResponseDTO dto = mapper.getShelterInfo(idx);
		if (dto != null && dto.getDescription() != null) {
			dto.setDescription(dto.getDescription().replaceAll("\n", "<br>"));
		}
		return dto;
	}
	
	@Override
	public int updateSheterInfo(ShelterInfoUpdateRequestDTO dto) {
		int count = mapper.updateSheterInfo(dto);
		return count;
	}
}
