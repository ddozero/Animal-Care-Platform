package com.animal.api.management.dashboard.service;

import java.util.List;

import com.animal.api.management.dashboard.model.response.SheltervolunteerDashboardResponseDTO;

public interface ShelterDashboardService {

	public List<SheltervolunteerDashboardResponseDTO> getVolunteerDashboard(int idx);
}
