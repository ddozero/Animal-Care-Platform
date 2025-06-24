package com.animal.api.management.dashboard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.animal.api.management.dashboard.model.response.ShelterAnimalDashboardResponseDTO;
import com.animal.api.management.dashboard.model.response.ShelterViewDashboardResponseDTO;
import com.animal.api.management.dashboard.model.response.ShelterVolunteerDashboardResponseDTO;

@Mapper
public interface ShelterDashboardMapper {

	public List<ShelterVolunteerDashboardResponseDTO> getVolunteerDashboard(int idx);

	public ShelterAnimalDashboardResponseDTO getAdoptionDashboard(int idx);

	public List<ShelterViewDashboardResponseDTO> getViewDashboard(int idx);

}
