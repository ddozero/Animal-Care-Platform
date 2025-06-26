package com.animal.api.admin.shelter.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.animal.api.admin.shelter.model.response.ShelterJoinRequestListResponseDTO;

@Mapper
public interface AdminShelterMapper {

	public List<ShelterJoinRequestListResponseDTO> getShelterJoinRequestList(Map map);

	public ShelterJoinRequestListResponseDTO getShelterJoinRequestDetail(int idx);
}
