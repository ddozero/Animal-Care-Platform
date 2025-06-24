package com.animal.api.management.dashboard.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.management.dashboard.mapper.ShelterDashboardMapper;
import com.animal.api.management.dashboard.model.response.ShelterAnimalDashboardResponseDTO;
import com.animal.api.management.dashboard.model.response.ShelterVolunteerDashboardResponseDTO;

@Service
@Primary
public class ShelterDashboardServiceImple implements ShelterDashboardService {
	@Autowired
	private ShelterDashboardMapper mapper;

	@Override
	public List<ShelterVolunteerDashboardResponseDTO> getVolunteerDashboard(int idx) {
		Calendar now = Calendar.getInstance();

		int currentYear = now.get(Calendar.YEAR); // 현재 년도
		int currentMonth = now.get(Calendar.MONTH) + 1; // 현재 월

		int yearArray[] = new int[6]; // 지난 6개월의 년도 정보 배열
		int monthArray[] = new int[6]; // 지난 6개월의 월 정보 배열
		String dateArray[] = new String[6]; // DB에 나오는 데이터와 같게 문자열 년월 배열

		// 현재 년월을 기준으로 선언된 배열에 지난 6개월의 정보를 삽입
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

		List<ShelterVolunteerDashboardResponseDTO> dashboardList = mapper.getVolunteerDashboard(idx);

		// 반환할 리스트가 비어있으면 로직 수행 없이 반환
		if (dashboardList.size() == 0) {
			return dashboardList;
		}

		// DB에서 나온 정보 중 비어있는 월이 있다면 지난 6개월 배열과 비교하여 없는 월에 0 값으로 생성
		if (dashboardList.size() != 6) {
			for (int i = 0; i < 6; i++) {
				if (i != 5) {
					if (!dateArray[i].equals(dashboardList.get(i).getMonth())) {
						ShelterVolunteerDashboardResponseDTO dto = new ShelterVolunteerDashboardResponseDTO(
								dateArray[i], 0);
						dashboardList.add(i, dto);
					}
				} else {
					ShelterVolunteerDashboardResponseDTO dto = new ShelterVolunteerDashboardResponseDTO(dateArray[i],
							0);
					dashboardList.add(dto);
				}
			}
		}

		return dashboardList;
	}

	@Override
	public ShelterAnimalDashboardResponseDTO getAdoptionDashboard(int idx) {
		ShelterAnimalDashboardResponseDTO dto = mapper.getAdoptionDashboard(idx);

		double adoptionRate = ((double) dto.getAdoptedAnimals() / (double) dto.getTotalAnimals()) * 100;

		dto.setAdoptionRate(adoptionRate);
		return dto;
	}
}
