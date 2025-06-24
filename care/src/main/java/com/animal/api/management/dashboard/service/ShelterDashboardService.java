package com.animal.api.management.dashboard.service;

import java.util.List;

import com.animal.api.management.dashboard.model.response.ShelterAnimalDashboardResponseDTO;
import com.animal.api.management.dashboard.model.response.ShelterVolunteerDashboardResponseDTO;

public interface ShelterDashboardService {

	public List<ShelterVolunteerDashboardResponseDTO> getVolunteerDashboard(int idx);
	
	public ShelterAnimalDashboardResponseDTO getAdoptionDashboard(int idx);
}
