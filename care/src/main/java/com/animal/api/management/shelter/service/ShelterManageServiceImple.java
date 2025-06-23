package com.animal.api.management.shelter.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.animal.api.management.shelter.mapper.ManagementShelterMapper;
import com.animal.api.management.shelter.model.request.ManageVolunteerReplyRequestDTO;
import com.animal.api.management.shelter.model.request.ShelterInfoUpdateRequestDTO;
import com.animal.api.management.shelter.model.response.AllManageShelterResponseDTO;
import com.animal.api.management.shelter.model.response.ManageAdoptionReviewResponseDTO;
import com.animal.api.management.shelter.model.response.ManageVolunteerReviewResponseDTO;

@Service
@Primary
public class ShelterManageServiceImple implements ShelterManageService {

	@Autowired
	private ManagementShelterMapper mapper;

	@Override
	public AllManageShelterResponseDTO getShelterInfo(int idx) {
		AllManageShelterResponseDTO dto = mapper.getShelterInfo(idx);
		if (dto != null && dto.getDescription() != null) {
			dto.setDescription(dto.getDescription().replaceAll("\n", "<br>"));
		}
		return dto;
	}

	@Override
	public int updateSheterInfo(ShelterInfoUpdateRequestDTO dto) {
		int count = mapper.updateSheterInfo(dto);
		return count;
	}

	@Override
	public List<ManageVolunteerReviewResponseDTO> getVolunteerReview(int listSize, int cp, int idx) {
		Map<String, Integer> map = new HashMap<String, Integer>();

		map.put("listSize", listSize);
		map.put("cp", cp);
		map.put("idx", idx);

		List<ManageVolunteerReviewResponseDTO> reviewLists = mapper.getVolunteerReview(map);
		return reviewLists;
	}

	@Override
	public List<ManageAdoptionReviewResponseDTO> getAdoptionReview(int listSize, int cp, int idx) {
		Map<String, Integer> map = new HashMap<String, Integer>();

		map.put("listSize", listSize);
		map.put("cp", cp);
		map.put("idx", idx);

		List<ManageAdoptionReviewResponseDTO> reviewLists = mapper.getAdoptionReview(map);
		return reviewLists;
	}

	@Override
	public int updateTurn(int ref, int turn) {
		Map<String, Integer> map = new HashMap<>();
		map.put("ref", ref);
		map.put("turn", turn);

		int count = mapper.updateTurn(map);

		if (count > 0) {
			return REPLY_OK;
		} else if (count == 0) {
			return NOT_REVIEW;
		} else {
			return REPLY_ERROR;
		}
	}

	@Override
	@Transactional
	public int addVolunterReviewApply(ManageVolunteerReplyRequestDTO dto) {

		int turnResult = updateTurn(dto.getRef(), dto.getTurn());

		if (turnResult != REPLY_OK) {
			return turnResult;
		}

		dto.setTurn(dto.getTurn() + 1);
		dto.setLev(dto.getLev() + 1);

		int result = mapper.addVolunterReviewApply(dto);

		if (result > 0) {
			return REPLY_OK;
		} else {
			return REPLY_ERROR;
		}
	}

	@Override
	public int updateVolunterReviewApply(ManageVolunteerReplyRequestDTO dto) {
		int count = mapper.updateVolunterReviewApply(dto);
		return count;
	}

}
