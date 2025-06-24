package com.animal.api.management.shelter.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.animal.api.management.shelter.mapper.ManagementShelterMapper;
import com.animal.api.management.shelter.model.request.ManageAdoptionReplyRequestDTO;
import com.animal.api.management.shelter.model.request.ManageVolunteerReplyRequestDTO;
import com.animal.api.management.shelter.model.request.ShelterInfoUpdateRequestDTO;
import com.animal.api.management.shelter.model.response.AllManageShelterResponseDTO;
import com.animal.api.management.shelter.model.response.ManageAdoptionReviewResponseDTO;
import com.animal.api.management.shelter.model.response.ManageVolunteerReviewResponseDTO;
import com.animal.api.management.shelter.model.response.ShelterBoardResponseDTO;

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
	@Transactional
	public int addVolunteerReviewApply(ManageVolunteerReplyRequestDTO dto, int userIdx, int reviewIdx) {

		Map<String, Integer> map = new HashMap<>();
		map.put("ref", dto.getRef());
		map.put("turn", dto.getTurn());

		int count = mapper.updateTurnAR(map);

		if (count < 0) {
			return REPLY_ERROR;
		} else if (count == 0) {
			return NOT_REVIEW;
		}

		dto.setTurn(dto.getTurn() + 1);
		dto.setLev(dto.getLev() + 1);

		dto.setUserIdx(userIdx);
		dto.setReviewIdx(dto.getRef());

		Integer shelterCheck = mapper.checkShelterUserVR(dto);
		if (shelterCheck == null || shelterCheck == 0) { // 해당 보호소 관리자인지 확인
			return NOT_SHELTER_MANAGER;
		}

		int result = mapper.addVolunteerReviewApply(dto);

		if (result > 0) {
			return UPDATE_OK;
		} else {
			return REPLY_ERROR;
		}
	}

	@Override
	public int updateVolunteerReviewApply(ManageVolunteerReplyRequestDTO dto, int userIdx, int reviewIdx) {

		Integer reviewCheck = mapper.checkVolunteerReview(reviewIdx);

		if (reviewCheck == null) { // 리뷰글이 있는지 확인
			return NOT_REVIEW;
		}

		dto.setReviewIdx(reviewIdx);
		dto.setUserIdx(userIdx);

		Integer shelterCheck = mapper.checkShelterUserVR(dto);
		if (shelterCheck == null || shelterCheck == 0) { // 해당 보호소 관리자인지 확인
			return NOT_SHELTER_MANAGER;
		}

		int count = mapper.updateVolunteerReviewApply(dto);
		if (count > 0) {
			return UPDATE_OK;
		} else {
			return REPLY_ERROR;
		}
	}

	@Override
	public int deleteVolunteerReviewApply(int userIdx, int reviewIdx) {

		Integer reviewCheck = mapper.checkVolunteerReview(reviewIdx);
		if (reviewCheck == null) {// 리뷰글이 있는지 확인
			return NOT_REVIEW;
		}

		ManageVolunteerReplyRequestDTO dto = new ManageVolunteerReplyRequestDTO();
		dto.setReviewIdx(reviewIdx);
		dto.setUserIdx(userIdx);

		Integer shelterCheck = mapper.checkShelterUserVR(dto);
		if (shelterCheck == null || shelterCheck == 0) {// 해당 보호소 관리자인지 확인 
			return NOT_SHELTER_MANAGER;
		}
		
		int count = mapper.deleteVolunteerReviewApply(dto);
		if (count > 0) {
			return DELETE_OK;
		} else {
			return REPLY_ERROR;
		}
	}

	@Override
	public int addAdoptionReviewApply(ManageAdoptionReplyRequestDTO dto, int userIdx, int reviewIdx) {

		Map<String, Integer> map = new HashMap<>();
		map.put("ref", dto.getRef());
		map.put("turn", dto.getTurn());

		int count = mapper.updateTurnAR(map);

		if (count < 0) {
			return REPLY_ERROR;
		} else if (count == 0) {// 리뷰글이 있는지 확인
			return NOT_REVIEW;
		}

		dto.setTurn(dto.getTurn() + 1);
		dto.setLev(dto.getLev() + 1);

		dto.setUserIdx(userIdx);
		dto.setReviewIdx(dto.getRef());
		Integer shelterCheck = mapper.checkShelterUserAR(dto);
		if (shelterCheck == null || shelterCheck == 0) {// 해당 보호소 관리자인지 확인
			return NOT_SHELTER_MANAGER;
		}

		int result = mapper.addAdoptionReviewApply(dto);

		if (result > 0) {
			return UPDATE_OK;
		} else {
			return REPLY_ERROR;
		}
	}
	
	@Override
	public int updateAdoptionReviewApply(ManageAdoptionReplyRequestDTO dto, int userIdx, int reviewIdx) {
		
		Integer reviewCheck = mapper.checkAdoptionReview(reviewIdx);

		if (reviewCheck == null) { // 리뷰글이 있는지 확인
			return NOT_REVIEW;
		}

		dto.setReviewIdx(reviewIdx);
		dto.setUserIdx(userIdx);

		Integer shelterCheck = mapper.checkShelterUserAR(dto);
		if (shelterCheck == null || shelterCheck == 0) { // 해당 보호소 관리자인지 확인
			return NOT_SHELTER_MANAGER;
		}

		int count = mapper.updateAdoptionReviewApply(dto);
		if (count > 0) {
			return UPDATE_OK;
		} else {
			return REPLY_ERROR;
		}
	}
	
	@Override
	public int deleteAdoptionReviewApply(int userIdx, int reviewIdx) {
		
		Integer reviewCheck = mapper.checkAdoptionReview(reviewIdx);
		if (reviewCheck == null) {// 리뷰글이 있는지 확인
			return NOT_REVIEW;
		}

		ManageAdoptionReplyRequestDTO dto = new ManageAdoptionReplyRequestDTO();
		dto.setReviewIdx(reviewIdx);
		dto.setUserIdx(userIdx);
		
		Integer shelterCheck = mapper.checkShelterUserAR(dto);
		if (shelterCheck == null || shelterCheck == 0) {// 해당 보호소 관리자인지 확인 
			return NOT_SHELTER_MANAGER;
		}
		
		int count = mapper.deleteAdoptionReviewApply(dto);
		if (count > 0) {
			return DELETE_OK;
		} else {
			return REPLY_ERROR;
		}
	}
	
	@Override
	public List<ShelterBoardResponseDTO> getShelterboardList(int userIdx, int listSize, int cp) {
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		map.put("userIdx", userIdx);
		map.put("listSize", listSize);
		map.put("cp", cp);
		
		List<ShelterBoardResponseDTO> boardLists = mapper.getShelterboardList(map);
		
		return boardLists;
	}


}
