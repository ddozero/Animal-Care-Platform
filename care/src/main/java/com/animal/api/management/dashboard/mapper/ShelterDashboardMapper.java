package com.animal.api.management.dashboard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.animal.api.management.dashboard.model.response.SheltervolunteerDashboardResponseDTO;

@Mapper
public interface ShelterDashboardMapper {

	public List<SheltervolunteerDashboardResponseDTO> getVolunteerDashboard(int idx);

}
