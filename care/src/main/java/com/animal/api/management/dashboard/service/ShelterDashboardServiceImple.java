package com.animal.api.management.dashboard.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.management.dashboard.mapper.ShelterDashboardMapper;
import com.animal.api.management.dashboard.model.response.ShelterAnimalDashboardResponseDTO;
import com.animal.api.management.dashboard.model.response.ShelterViewDashboardResponseDTO;
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
		Map<String, Integer> monthMap = new HashMap<>();
		for (ShelterVolunteerDashboardResponseDTO dto : dashboardList) {
		    monthMap.put(dto.getMonth(), dto.getCount());
		}

		dashboardList.clear(); // 리스트를 다시 구성
		for (String date : dateArray) {
		    int count = monthMap.getOrDefault(date, 0);
		    dashboardList.add(new ShelterVolunteerDashboardResponseDTO(date, count));
		}

		return dashboardList;
	}

	@Override
	public ShelterAnimalDashboardResponseDTO getAdoptionDashboard(int idx) {
		ShelterAnimalDashboardResponseDTO dto = mapper.getAdoptionDashboard(idx);
		// 입양율 백분율 계산
		double adoptionRate = ((double) dto.getAdoptedAnimals() / (double) dto.getTotalAnimals()) * 100;
		dto.setAdoptionRate(adoptionRate);
		return dto;
	}

	@Override
	public List<ShelterViewDashboardResponseDTO> getViewDashboard(int idx) {
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

		List<ShelterViewDashboardResponseDTO> dashboardList = mapper.getViewDashboard(idx);

		// 반환할 리스트가 비어있으면 로직 수행 없이 반환
		if (dashboardList.size() == 0) {
			return dashboardList;
		}

		// DB에서 나온 정보 중 비어있는 월이 있다면 지난 6개월 배열과 비교하여 없는 월에 0 값으로 생성
		if (dashboardList.size() != 6) {
		    // 1. 기존 데이터를 Map으로 변환
		    Map<String, Integer> monthMap = new HashMap<>();
		    for (ShelterViewDashboardResponseDTO dto : dashboardList) {
		        monthMap.put(dto.getMonth(), dto.getViews());
		    }

		    // 2. 기존 리스트 비우고, 날짜 기준으로 새로 채움
		    dashboardList.clear();
		    for (String date : dateArray) {
		        int count = monthMap.getOrDefault(date, 0);
		        dashboardList.add(new ShelterViewDashboardResponseDTO(date, count));
		    }
		}

		return dashboardList;
	}
}
