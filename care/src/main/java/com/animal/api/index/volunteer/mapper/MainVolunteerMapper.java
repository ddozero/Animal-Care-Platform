package com.animal.api.index.volunteer.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.animal.api.index.volunteer.model.response.MainVolunteerSummaryDTO;

@Mapper
public interface MainVolunteerMapper {

	List<MainVolunteerSummaryDTO> selectRecentVolunteers();
}
