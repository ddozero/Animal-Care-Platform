package com.animal.api.shelter.service;

import java.util.List;

import com.animal.api.shelter.model.response.AllShelterListResponseDTO;
import com.animal.api.shelter.model.response.ShelterAnimalsResponseDTO;
import com.animal.api.shelter.model.response.ShelterBoardListResponseDTO;
import com.animal.api.shelter.model.response.ShelterDetailResponseDTO;
import com.animal.api.shelter.model.response.ShelterVolunteersResponseDTO;

public interface UserShelterService {

	public List<AllShelterListResponseDTO> getAllShelters(int listSize, int cp);

	public List<AllShelterListResponseDTO> searchShelters(int listSize, int cp, String shelterName, String shelterAddress,
			String shelterType);

	public ShelterDetailResponseDTO getShelterDetail(int idx);

	public List<ShelterVolunteersResponseDTO> getShelterVolunteers(int listSize, int cp, int idx);

	public List<ShelterAnimalsResponseDTO> getAllShelterAnimals(int listSize, int cp, int idx);

	public List<ShelterAnimalsResponseDTO> searchShelterAnimals(int idx, int listSize, int cp, String type, String breed,
			String gender, int neuter, int age, String adoptionStatus, String personality, int size, String name);

	public List<ShelterBoardListResponseDTO> getShelterBoards(int listSize, int cp, int idx);
}
