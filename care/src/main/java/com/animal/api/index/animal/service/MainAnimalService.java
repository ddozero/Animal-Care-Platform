package com.animal.api.index.animal.service;

import java.util.List;

import com.animal.api.index.animal.model.response.MainAnimalResponseDTO;

public interface MainAnimalService {
	
	List<MainAnimalResponseDTO> getRecentAnimals(int limit);

}
