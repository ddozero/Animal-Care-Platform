package com.animal.api.shelter.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.animal.api.shelter.model.response.AllShelterListDTO;

@Mapper
public interface UserShelterMapper {

	public List<AllShelterListDTO> getAllShelters(Map map);
}
