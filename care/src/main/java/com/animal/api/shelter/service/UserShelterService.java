package com.animal.api.shelter.service;

import java.util.List;

import com.animal.api.shelter.model.response.AllShelterListDTO;

public interface UserShelterService {

	public List<AllShelterListDTO> getAllShelters(int listSize, int cp);
}
