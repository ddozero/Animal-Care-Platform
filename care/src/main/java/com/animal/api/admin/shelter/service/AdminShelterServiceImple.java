package com.animal.api.admin.shelter.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.admin.shelter.mapper.AdminShelterMapper;
import com.animal.api.admin.shelter.model.response.ShelterJoinRequestListResponseDTO;
import com.animal.api.common.model.PageInformationDTO;
import com.animal.api.common.util.FileManager;

@Service
@Primary
public class AdminShelterServiceImple implements AdminShelterService {
	@Autowired
	private AdminShelterMapper mapper;
	@Autowired
	private FileManager fileManager;

	private int listSize = 5;
	private int pageSize = 5;

	@Override
	public int deleteVolunteer(int volunteerIdx, int shelterIdx) {
		Integer userIdx = mapper.checkShelterVolunteer(volunteerIdx);

		if (userIdx == null) {
			return NOT_FOUND_VOLUNTEER;
		}

		if (userIdx != shelterIdx) { // 해당 보호시설의 봉사인지 확인
			return NOT_OWNED_VOLUNTEER;
		}
		int result = mapper.deleteVolunteer(volunteerIdx);

		result = result > 0 ? DELETE_SUCCESS : ERROR;
		if (result == DELETE_SUCCESS) { // 봉사 삭제 시 파일도 같이 삭제
			fileManager.deleteFolder("volunteers", volunteerIdx);
		}
		return result;
	}

	@Override
	public List<ShelterJoinRequestListResponseDTO> getShelterJoinRequestList(int cp) {
		if (cp == 0) {
			cp = 1;
		}
		cp = (cp - 1) * listSize;

		Map<String, Integer> map = new HashMap<String, Integer>();

		map.put("listSize", listSize);
		map.put("cp", cp);

		List<ShelterJoinRequestListResponseDTO> requestList = mapper.getShelterJoinRequestList(map);

		if (requestList != null) {
			for (ShelterJoinRequestListResponseDTO dto : requestList) {
				List<String> filePaths = fileManager.getFilePath("users", dto.getIdx());
				if (filePaths != null || filePaths.size() != 0) {
					dto.setFilePath(filePaths.get(0));
				}
			}
		}
		return requestList;
	}

	@Override
	public PageInformationDTO getShelterJoinRequestListPageInfo(int cp) {
		if (cp == 0) {
			cp = 1;
		}
		int totalCnt = mapper.getShelterJoinRequestListTotalCnt();
		PageInformationDTO pageInfo = new PageInformationDTO(totalCnt, listSize, pageSize, cp);
		return pageInfo;
	}

	@Override
	public ShelterJoinRequestListResponseDTO getShelterJoinRequestDetail(int idx) {
		ShelterJoinRequestListResponseDTO dto = mapper.getShelterJoinRequestDetail(idx);
		if (dto != null) {
			List<String> filePaths = fileManager.getFilePath("users", dto.getIdx());
			if (filePaths != null || filePaths.size() != 0) {
				dto.setFilePath(filePaths.get(0));
			}
		}
		return dto;
	}

	@Override
	public int updateShelterJoinRequestStatus(int idx) {
		Integer checkStatus = mapper.checkJoinStatus(idx);

		if (checkStatus == null) {
			return NOT_REQUEST;
		} else if (checkStatus == 1) {
			return APPROVED;
		} else if (checkStatus == -1) {
			return WITHDRAWN;
		}

		int result = mapper.updateShelterJoinRequestStatus(idx);

		return result > 0 ? UPDATE_SUCCESS : ERROR;
	}

	@Override
	public int ShelterJoinRejection(int idx) {
		Integer checkStatus = mapper.checkJoinStatus(idx);

		if (checkStatus == null) {
			return NOT_REQUEST;
		} else if (checkStatus == 1) {
			return APPROVED;
		} else if (checkStatus == -1) {
			return WITHDRAWN;
		}

		return NOT_ERROR;
	}
}
