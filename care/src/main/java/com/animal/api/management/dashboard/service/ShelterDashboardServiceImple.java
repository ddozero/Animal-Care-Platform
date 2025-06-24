package com.animal.api.management.dashboard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.management.dashboard.mapper.ShelterDashboardMapper;

@Service
@Primary
public class ShelterDashboardServiceImple implements ShelterDashboardService {
	@Autowired
	private ShelterDashboardMapper mapper;
}
