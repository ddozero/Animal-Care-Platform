package com.animal.api.index.shelter.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.animal.api.index.shelter.model.response.MainShelterResponseDTO;

@Mapper
public interface MainShelterMapper {

	List<MainShelterResponseDTO> selectSheltersByRegion(@Param("region") String region);
	int countSheltersByRegion(@Param("region") String region);
	
	int countAllShelters();
}
