package com.animal.api.index.shelter.service;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.index.shelter.mapper.MainShelterMapper;
import com.animal.api.index.shelter.model.response.MainShelterResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@Primary
@RequiredArgsConstructor
public class MainShelterServiceImpl implements MainShelterService {

	private final MainShelterMapper mainShelterMapper;
	
	@Override
	public List<MainShelterResponseDTO> getSheltersByRegion(String region) {
		return mainShelterMapper.selectSheltersByRegion(region);
	}
	
	@Override
	public int countSheltersByRegion(String region) {
	    return mainShelterMapper.countSheltersByRegion(region);
	}
	
	@Override
	public int countAllShelters() {
		return mainShelterMapper.countAllShelters();
	}

}
