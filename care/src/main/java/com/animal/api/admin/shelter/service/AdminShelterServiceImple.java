package com.animal.api.admin.shelter.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.admin.shelter.mapper.AdminShelterMapper;
import com.animal.api.admin.shelter.model.response.ShelterJoinRequestListResponseDTO;
import com.animal.api.common.util.FileManager;

@Service
@Primary
public class AdminShelterServiceImple implements AdminShelterService {
	@Autowired
	private AdminShelterMapper mapper;
	@Autowired
	private FileManager fileManager;

	@Override
	public List<ShelterJoinRequestListResponseDTO> getShelterJoinRequestList(int listSize, int cp) {
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
}
