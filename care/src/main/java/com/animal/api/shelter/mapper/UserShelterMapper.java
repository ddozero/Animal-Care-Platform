package com.animal.api.shelter.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.animal.api.shelter.model.request.SearchShelterAnimalRequestDTO;
import com.animal.api.shelter.model.request.SearchShelterRequestDTO;
import com.animal.api.shelter.model.response.AllShelterListDTO;
import com.animal.api.shelter.model.response.ShelterAnimalsDTO;
import com.animal.api.shelter.model.response.ShelterDetailDTO;
import com.animal.api.shelter.model.response.ShelterVolunteersDTO;

@Mapper
public interface UserShelterMapper {

	public List<AllShelterListDTO> getAllShelters(Map map);

	public List<AllShelterListDTO> searchShelters(SearchShelterRequestDTO dto);

	public ShelterDetailDTO getShelterDetail(int idx);

	public List<ShelterVolunteersDTO> getShelterVolunteers(Map map);

	public List<ShelterAnimalsDTO> getAllShelterAnimals(Map map);

	public List<ShelterAnimalsDTO> searchShelterAnimals(SearchShelterAnimalRequestDTO dto);
}
