package com.animal.api.shelter.service;

import java.util.List;

import com.animal.api.shelter.model.response.AllShelterListDTO;
import com.animal.api.shelter.model.response.ShelterAnimalsDTO;
import com.animal.api.shelter.model.response.ShelterDetailDTO;
import com.animal.api.shelter.model.response.ShelterVolunteersDTO;

public interface UserShelterService {

	public List<AllShelterListDTO> getAllShelters(int listSize, int cp);

	public List<AllShelterListDTO> searchShelters(int listSize, int cp, String shelterName, String shelterAddress,
			String shelterType);

	public ShelterDetailDTO getShelterDetail(int idx);

	public List<ShelterVolunteersDTO> getShelterVolunteers(int listSize, int cp, int idx);

	public List<ShelterAnimalsDTO> getAllShelterAnimals(int listSize, int cp, int idx);

	public List<ShelterAnimalsDTO> searchShelterAnimals(int idx, int listSize, int cp, String type, String breed,
			String gender, int neuter, int age, String adoptionStatus, String personality, int size, String name);
}
