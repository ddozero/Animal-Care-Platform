package com.animal.api.mypage.volunteers.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.animal.api.mypage.volunteers.model.response.VolunteerDetailResponseDTO;
import com.animal.api.mypage.volunteers.model.response.VolunteerListResponseDTO;

@Mapper
public interface VolunteerMapper {

	//봉사내역 리스트
	List<VolunteerListResponseDTO> findVolunteerListByUserIdx (int userIdx);
	
	//봉사 상세 내역
	VolunteerDetailResponseDTO findVolunteerDetailByRequestIdx(@Param("volunteerRequestIdx") int volunteerRequestIdx);
}
