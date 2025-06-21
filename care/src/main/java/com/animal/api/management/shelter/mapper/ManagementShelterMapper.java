package com.animal.api.management.shelter.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.animal.api.management.shelter.model.response.AllManageShelterDTO;

@Mapper
public interface ManagementShelterMapper {

	public AllManageShelterDTO getShelterInfo(int userIdx);
	
	public int updateSheterInfo(AllManageShelterDTO dto);

}
