package com.animal.api.volunteers.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.animal.api.common.model.PageInformationDTO;
import com.animal.api.common.util.FileManager;
import com.animal.api.volunteers.mapper.UserVolunteersMapper;
import com.animal.api.volunteers.model.request.SearchVolunteerRequestDTO;
import com.animal.api.volunteers.model.request.VolunteersSubmitRequestDTO;
import com.animal.api.volunteers.model.response.AllVolunteersResponseDTO;

@Service
@Primary
public class UserVolunteersServcieImple implements UserVolunteersService {

	@Autowired
	private UserVolunteersMapper mapper;

	@Autowired
	private FileManager fileManager;

	private int listSize = 10;
	private int pageSize = 5;

	@Override
	public List<AllVolunteersResponseDTO> getAllVolunteers(int cp) {
		Map<String, Integer> map = new HashMap<String, Integer>();

		if (cp == 0) {
			cp = 1;
		}
		cp = (cp - 1) * listSize;

		map.put("listSize", listSize);
		map.put("cp", cp);

		List<AllVolunteersResponseDTO> volunteerLists = mapper.getAllVolunteers(map);
		return volunteerLists;
	}

	@Override
	public PageInformationDTO getAllVolunteersPage(int cp) {
		if (cp == 0) {
			cp = 1;
		}
		int totalCnt = mapper.getAllVolunteersTotalCnt();

		PageInformationDTO pageInfo = new PageInformationDTO(totalCnt, listSize, pageSize, cp);
		return pageInfo;
	}

	@Override
	public AllVolunteersResponseDTO getVolunteersDetail(int idx) {
		AllVolunteersResponseDTO dto = mapper.getVolunteersDetail(idx);

		if (dto != null && dto.getContent() != null) {
			List<String> imagePaths = fileManager.getImagePath("volunteers", idx);
			if (imagePaths != null || imagePaths.size() != 0) {
				dto.setImagePath(imagePaths.get(0));
			}
			dto.setFilePaths(fileManager.getFilePath("volunteers", idx));
		}
		return dto;
	}

	@Override
	public List<AllVolunteersResponseDTO> searchVolunteers(int cp, String title, String content, String location,
			String status, String shelter, String shelterType, LocalDate volunteerDate, String type, int time) {

		SearchVolunteerRequestDTO dto = new SearchVolunteerRequestDTO(cp, listSize, title, content, location, status,
				shelter, shelterType, volunteerDate, type, time);
		List<AllVolunteersResponseDTO> searchVolunteersList = mapper.searchVolunteers(dto);

		return searchVolunteersList;
	}

	@Override
	public PageInformationDTO getSearchVolunteersPage(int cp, String title, String content, String location,
			String status, String shelter, String shelterType, LocalDate volunteerDate, String type, int time) {

		SearchVolunteerRequestDTO dto = new SearchVolunteerRequestDTO(cp, listSize, title, content, location, status,
				shelter, shelterType, volunteerDate, type, time);
		if (cp == 0) {
			cp = 1;
		}
		int totalCnt = mapper.getSearchVolunteerTotalcnt(dto);

		PageInformationDTO pageInfo = new PageInformationDTO(totalCnt, listSize, pageSize, cp);
		return pageInfo;
	}

	@Transactional
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

	@Override
	public List<AllVolunteersResponseDTO> getVolunteerCalendar() {

		List<AllVolunteersResponseDTO> listsCal = mapper.getVolunteerCalendar();
		return listsCal;
	}

}
