package com.animal.api.management.animal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.management.animal.mapper.ShelterAnimalsMapper;

@Service
@Primary
public class ShelterAnimalsServiceImple implements ShelterAnimalsService {

	@Autowired
	private ShelterAnimalsMapper mapper;
}
