package com.animal.api.management.shelter.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.animal.api.management.shelter.model.request.ShelterInfoUpdateRequestDTO;
import com.animal.api.management.shelter.model.response.AllManageShelterResponseDTO;

@Mapper
public interface ManagementShelterMapper {

	public AllManageShelterResponseDTO getShelterInfo(int userIdx);

	public int updateSheterInfo(ShelterInfoUpdateRequestDTO dto);

}
