package com.animal.api.volunteers.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.common.util.FileManager;
import com.animal.api.volunteers.mapper.UserVolunteersMapper;
import com.animal.api.volunteers.model.request.VolunteersSubmitRequestDTO;
import com.animal.api.volunteers.model.response.AllVolunteersResponseDTO;
import com.animal.api.volunteers.model.response.SearchVolunteerResponseDTO;

@Service
@Primary
public class UserVolunteersServcieImple implements UserVolunteersService {

	@Autowired
	private UserVolunteersMapper mapper;
	
	@Autowired
	private FileManager fileManager;

	@Override
	public List<AllVolunteersResponseDTO> getAllVolunteers(int listSize, int cp) {
		Map<String, Integer> map = new HashMap<String, Integer>();

		map.put("listSize", listSize);
		map.put("cp", cp);

		List<AllVolunteersResponseDTO> volunteerLists = mapper.getAllVolunteers(map);
		return volunteerLists;
	}

	@Override
	public AllVolunteersResponseDTO getVolunteersDetail(int idx) {
		AllVolunteersResponseDTO dto = mapper.getVolunteersDetail(idx);
		if (dto != null && dto.getContent() != null) {
			dto.setImagePaths(fileManager.getImagePath("volunteers", idx));
			dto.setFilePaths(fileManager.getFilePath("volunteers", idx));
			dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
		}
		return dto;
	}

	@Override
	public List<AllVolunteersResponseDTO> searchVolunteers(int listSize, int cp, String title, String content,
			String location, String status, String shelter, String shelterType, Timestamp volunteerDate, String type,
			int time) {

		SearchVolunteerResponseDTO dto = new SearchVolunteerResponseDTO(cp, listSize, title, content, location, status,
				shelter, shelterType, volunteerDate, type, time);
		List<AllVolunteersResponseDTO> searchVolunteersList = mapper.searchVolunteers(dto);

		return searchVolunteersList;
	}

	@Override
	public int submitVolunteers(VolunteersSubmitRequestDTO dto) {

		int checkIdx = mapper.checkSubmit(dto.getUserIdx(), dto.getVolunteerIdx());
		if (checkIdx > 0) {
			return SUBMIT_DUPLICATE;
		}

		String getVolunteerStatus = mapper.getVolunteerStatus(dto.getVolunteerIdx());
		if (getVolunteerStatus == null) {
			return SUBMIT_ERROR;
		}

		int checkStatus = 0;

		try {
			checkStatus = Integer.parseInt(getVolunteerStatus);
		} catch (NumberFormatException e) {
			return SUBMIT_ERROR;
		}

		if (checkStatus == 3 || checkStatus == 4) {
			return SUBMIT_NOT_OK;
		}

		int result = mapper.submitVolunteers(dto);

		if (result > 0) {
			mapper.updateApplicants(dto);
			return SUBMIT_OK;
		} else {
			return SUBMIT_ERROR;
		}

	}

}
