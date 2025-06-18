package com.animal.api.animal.service;

import java.util.List;

import com.animal.api.animal.model.response.AllAnimalListResponseDTO;

public interface UserAnimalService {

	public List<AllAnimalListResponseDTO> getAllAnimals(int listSize, int cp);

}
