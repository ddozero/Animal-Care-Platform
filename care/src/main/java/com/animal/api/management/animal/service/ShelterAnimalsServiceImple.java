package com.animal.api.management.animal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.management.animal.mapper.ShelterAnimalsMapper;
import com.animal.api.management.animal.model.response.AnimalAddShelterInfoResponseDTO;

@Service
@Primary
public class ShelterAnimalsServiceImple implements ShelterAnimalsService {

	@Autowired
	private ShelterAnimalsMapper mapper;

	@Override
	public AnimalAddShelterInfoResponseDTO getShelterProfile(int idx) {
		AnimalAddShelterInfoResponseDTO dto = mapper.getShelterProfile(idx);
		return dto;
	}
}
