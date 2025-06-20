package com.animal.api.volunteers.service;

import java.sql.Timestamp;
import java.util.List;

import com.animal.api.volunteers.model.response.AllVolunteersResponseDTO;

public interface UserVolunteersService {

	public List<AllVolunteersResponseDTO> getAllVolunteers(int listSize, int cp);

	public AllVolunteersResponseDTO getVolunteersDetail(int idx);

	public List<AllVolunteersResponseDTO> searchVolunteers(int listSize, int cp, String location, String status,
			String shelterName, String shelterType, Timestamp volunteerDate, String type, int time);

}
