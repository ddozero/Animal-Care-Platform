package com.animal.api.management.shelter.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.management.shelter.mapper.ManagementShelterMapper;
import com.animal.api.management.shelter.model.request.ShelterInfoUpdateRequestDTO;
import com.animal.api.management.shelter.model.response.AllManageShelterResponseDTO;
import com.animal.api.management.shelter.model.response.ManageVolunteerReviewResponseDTO;

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
	
	@Override
	public List<ManageVolunteerReviewResponseDTO> getVolunteerReviews(int listSize, int cp, int idx) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		map.put("listSize", listSize);
		map.put("cp", cp);
		map.put("idx", idx);
		
		List<ManageVolunteerReviewResponseDTO> reviewLists = mapper.getVolunteerReviews(map);
		return reviewLists;
	}
}
