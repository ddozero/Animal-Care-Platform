package com.animal.api.animal.service;

import java.util.List;

import com.animal.api.animal.model.request.AdoptionSubmitReqestDTO;
import com.animal.api.animal.model.response.AdoptionAnimalResponseDTO;
import com.animal.api.animal.model.response.AllAnimalListResponseDTO;
import com.animal.api.animal.model.response.AnimalDetailResponseDTO;

public interface UserAnimalService {

	static int RESERVATION_COMPLETED = 1; // 예약 신청 완료
	static int RESERVATION_UNAVAILABLE = 0; // 예약 불가능
	static int RESERVATION_FAILD = -1; // 에러

	public List<AllAnimalListResponseDTO> getAllAnimals(int listSize, int cp);

	public List<AllAnimalListResponseDTO> searchAnimals(int listSize, int cp, String type, String breed, String gender,
			int neuter, int age, String adoptionStatus, String personality, int size, String name);

	public AnimalDetailResponseDTO getAnimalDetail(int idx);

	public AdoptionAnimalResponseDTO getAdoptionInfo(int idx);

	public int submitAdoption(AdoptionSubmitReqestDTO dto);
}
