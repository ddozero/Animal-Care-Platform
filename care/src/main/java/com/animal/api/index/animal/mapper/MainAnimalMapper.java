package com.animal.api.index.animal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.animal.api.index.animal.model.response.MainAnimalResponseDTO;

@Mapper
public interface MainAnimalMapper {

	List<MainAnimalResponseDTO> selectRecentAnimals(@Param("limit") int limit);
}
