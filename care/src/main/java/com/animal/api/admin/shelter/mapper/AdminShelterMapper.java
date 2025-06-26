package com.animal.api.admin.shelter.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.animal.api.admin.shelter.model.response.ShelterJoinRequestListResponseDTO;

@Mapper
public interface AdminShelterMapper {

	public ShelterJoinRequestListResponseDTO getShelterJoinRequestList();
}
