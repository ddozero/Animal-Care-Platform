package com.animal.api.management.volunteers.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.animal.api.management.volunteers.model.request.ShelterVolunteerUpdateRequestDTO;
import com.animal.api.management.volunteers.model.request.ShelterVolunteersInsertDTO;
import com.animal.api.management.volunteers.model.response.CountApprovedResponseDTO;
import com.animal.api.management.volunteers.model.response.CountPeopleResponseDTO;
import com.animal.api.management.volunteers.model.response.ShelterVolunteerApplicationDetailResponseDTO;
import com.animal.api.management.volunteers.model.response.ShelterVolunteerApplicationsResponseDTO;
import com.animal.api.management.volunteers.model.response.ShelterVolunteerDetailResponseDTO;
import com.animal.api.management.volunteers.model.response.ShelterVolunteersListResponseDTO;

@Mapper
public interface ShelterVolunteersMappper {

	public List<ShelterVolunteersListResponseDTO> getShelterAllVolunteers(Map map);

	public int getShelterAllVolunteersTotalCnt(int userIdx);

	public int addShelterVolunteer(ShelterVolunteersInsertDTO dto);

	public ShelterVolunteerDetailResponseDTO getShelterVolunteerDetail(int volunteerIdx);

	public Integer checkMyVolunteer(int volunteerIdx);

	public int updateShelterVolunteer(ShelterVolunteerUpdateRequestDTO dto);

	public int deleteShelterVolunteer(int volunteerIdx);

	public Integer checkVolunteerExists(int volunteerIdx);

	public List<ShelterVolunteerApplicationsResponseDTO> getShelterVolunteerApplications(Map map);

	public int getShelterVolunteerApplicationsTotalCnt(int volunteerIdx);

	public ShelterVolunteerApplicationDetailResponseDTO getShelterVolunteerApplicationDetail(int applicationIdx);

	public List<CountApprovedResponseDTO> countApproved(int volunteerIdx);

	public Integer checkCapacity(int volunteerIdx);

	public CountPeopleResponseDTO getApplicationPeople(int applicationIdx);

	public int approveShelterVolunteerApplication(int applicationIdx);

	public int cancelShelterVolunteerApplication(int applicationIdx);
}
