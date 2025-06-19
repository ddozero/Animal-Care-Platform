package com.animal.api.shelter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.shelter.mapper.UserShelterMapper;

@Service
@Primary
public class UserShelterServiceImple implements UserShelterService {
	
	@Autowired
	private UserShelterMapper mapper;
}
