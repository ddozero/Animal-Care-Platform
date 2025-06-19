package com.animal.api.animal.service;

import java.util.List;

import com.animal.api.animal.model.response.AllAnimalListResponseDTO;

public interface UserAnimalService {

	public List<AllAnimalListResponseDTO> getAllAnimals(int listSize, int cp);

	public List<AllAnimalListResponseDTO> searchAnimals(int listSize, int cp, String type, String breed, int neuter,
			int age, String adoptionStatus, String personality, int size, String name);
}
