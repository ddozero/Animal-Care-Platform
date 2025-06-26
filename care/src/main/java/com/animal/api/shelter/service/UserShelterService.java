package com.animal.api.shelter.service;

import java.util.List;

import com.animal.api.common.model.PageInformationDTO;
import com.animal.api.shelter.model.request.SearchShelterAnimalRequestDTO;
import com.animal.api.shelter.model.response.AllShelterListResponseDTO;
import com.animal.api.shelter.model.response.ShelterAdoptionReviewResponseDTO;
import com.animal.api.shelter.model.response.ShelterAnimalsResponseDTO;
import com.animal.api.shelter.model.response.ShelterBoardDetailResponseDTO;
import com.animal.api.shelter.model.response.ShelterBoardListResponseDTO;
import com.animal.api.shelter.model.response.ShelterDetailResponseDTO;
import com.animal.api.shelter.model.response.ShelterVolunteerReviewResponseDTO;
import com.animal.api.shelter.model.response.ShelterVolunteersResponseDTO;

public interface UserShelterService {

	public List<AllShelterListResponseDTO> getAllShelters(int cp);

	public PageInformationDTO getAllSheltersPageInfo(int cp);

	public List<AllShelterListResponseDTO> searchShelters(int cp, String shelterName, String shelterAddress,
			String shelterType);

	public PageInformationDTO searchSheltersPageInfo(int cp, String shelterName, String shelterAddress,
			String shelterType);

	public ShelterDetailResponseDTO getShelterDetail(int idx);

	public List<ShelterVolunteersResponseDTO> getShelterVolunteers(int cp, int idx);

	public PageInformationDTO getShelterVolunteersPageInfo(int cp, int idx);

	public List<ShelterAnimalsResponseDTO> getAllShelterAnimals(int cp, int idx);

	public PageInformationDTO getAllShelterAnimalsPageInfo(int cp, int idx);

	public List<ShelterAnimalsResponseDTO> searchShelterAnimals(int idx, int cp, String type, String breed,
			String gender, int neuter, int age, String adoptionStatus, String personality, int size, String name);

	public PageInformationDTO searchShelterAnimalsPageInfo(int idx, int cp, String type, String breed, String gender,
			int neuter, int age, String adoptionStatus, String personality, int size, String name);

	public List<ShelterBoardListResponseDTO> getShelterBoards(int cp, int idx);

	public PageInformationDTO getShelterBoardsPageInfo(int cp, int idx);

	public ShelterBoardDetailResponseDTO getShelterBoardDetail(int idx);

	public List<ShelterVolunteerReviewResponseDTO> getShelterVolunteerReviews(int cp, int idx);

	public PageInformationDTO getShelterVolunteerReviewsPageInfo(int cp, int idx);

	public List<ShelterAdoptionReviewResponseDTO> getShelterAdoptionReviews(int cp, int idx);

	public PageInformationDTO getShelterAdoptionReviewsPageInfo(int cp, int idx);
}
