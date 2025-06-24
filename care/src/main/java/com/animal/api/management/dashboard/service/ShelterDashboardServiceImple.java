package com.animal.api.management.dashboard.service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.management.dashboard.mapper.ShelterDashboardMapper;
import com.animal.api.management.dashboard.model.response.SheltervolunteerDashboardResponseDTO;

@Service
@Primary
public class ShelterDashboardServiceImple implements ShelterDashboardService {
	@Autowired
	private ShelterDashboardMapper mapper;

	@Override
	public List<SheltervolunteerDashboardResponseDTO> getVolunteerDashboard(int idx) {
		Calendar now = Calendar.getInstance();

		int currentYear = now.get(Calendar.YEAR);
		int currentMonth = now.get(Calendar.MONTH) + 1;

		int yearArray[] = new int[6];
		int monthArray[] = new int[6];
		String dateArray[] = new String[6];

		int temp = 5;
		for (int i = 0; i < 6; i++) {
			monthArray[i] = currentMonth - temp;
			yearArray[i] = currentYear;
			if (monthArray[i] <= 0) {
				monthArray[i] += 12;
				yearArray[i] = currentYear - 1;
			}
			dateArray[i] = yearArray[i] + "-" + (monthArray[i] < 10 ? "0" + monthArray[i] : monthArray[i]);
			temp--;
		}

		List<SheltervolunteerDashboardResponseDTO> dashboardList = mapper.getVolunteerDashboard(idx);
		
		if (dashboardList == null) {
			return null;
		}

		if (dashboardList.size() != 6) {
			for (int i = 0; i < 6; i++) {
				if (i != 5) {
					if (!dateArray[i].equals(dashboardList.get(i).getMonth())) {
						SheltervolunteerDashboardResponseDTO dto = new SheltervolunteerDashboardResponseDTO(
								dateArray[i], 0);
						dashboardList.add(i, dto);
					}
				} else {
					SheltervolunteerDashboardResponseDTO dto = new SheltervolunteerDashboardResponseDTO(dateArray[i],
							0);
					dashboardList.add(dto);
				}
			}
		}

		return dashboardList;
	}
}
