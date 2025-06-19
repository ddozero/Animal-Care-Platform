package com.animal.api.animal.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.animal.api.animal.model.response.AllAnimalListResponseDTO;

@Mapper
public interface UserAnimalMapper {
	public List<AllAnimalListResponseDTO> getAllAnimals(Map map);
}
