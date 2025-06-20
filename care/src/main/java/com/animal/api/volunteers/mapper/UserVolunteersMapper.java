package com.animal.api.volunteers.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

import com.animal.api.volunteers.model.response.VolunteersListResponseDTO;

@Mapper
public interface UserVolunteersMapper {

	public List<VolunteersListResponseDTO> getAllVolunteers(Map map);

	public VolunteersListResponseDTO getVolunteersDetail(int idx);

}
