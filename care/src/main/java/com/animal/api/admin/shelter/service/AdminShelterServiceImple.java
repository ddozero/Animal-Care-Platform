package com.animal.api.admin.shelter.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.admin.shelter.mapper.AdminShelterMapper;
import com.animal.api.admin.shelter.model.response.ShelterJoinRequestListResponseDTO;
import com.animal.api.animal.model.response.AllAnimalListResponseDTO;
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
				dto.setFilePath(fileManager.getFilePath("users", dto.getIdx()).get(0));
			}
		}
		return requestList;
	}
}
