package com.animal.api.mypage.volunteers.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.animal.api.mypage.volunteers.model.response.VolunteerListResponseDTO;

@Mapper
public interface VolunteerMapper {

	List<VolunteerListResponseDTO> findVolunteerListByUserIdx (int userIdx);
}
