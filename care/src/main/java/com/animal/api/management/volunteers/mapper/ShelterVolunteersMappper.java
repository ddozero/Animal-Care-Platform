package com.animal.api.management.volunteers.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.animal.api.management.volunteers.model.request.ShelterVolunteerUpdateRequestDTO;
import com.animal.api.management.volunteers.model.request.ShelterVolunteersInsertDTO;
import com.animal.api.management.volunteers.model.response.ShelterVolunteerDetailResponseDTO;
import com.animal.api.management.volunteers.model.response.ShelterVolunteersListResponseDTO;

@Mapper
public interface ShelterVolunteersMappper {

	public List<ShelterVolunteersListResponseDTO> getShelterAllVolunteers(Map map);

	public int addShelterVolunteer(ShelterVolunteersInsertDTO dto);

	public ShelterVolunteerDetailResponseDTO getShelterVolunteerDetail(int volunteerIdx);
	
	public Integer checkMyVolunteer(int volunteerIdx);
	
	public int updateShelterVolunteer(ShelterVolunteerUpdateRequestDTO dto);
}
