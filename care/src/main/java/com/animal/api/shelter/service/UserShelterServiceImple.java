package com.animal.api.shelter.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.shelter.mapper.UserShelterMapper;
import com.animal.api.shelter.model.response.AllShelterListDTO;

@Service
@Primary
public class UserShelterServiceImple implements UserShelterService {

	@Autowired
	private UserShelterMapper mapper;

	@Override
	public List<AllShelterListDTO> getAllShelters(int listSize, int cp) {
		cp = changeCurrentPage(cp, listSize);

		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("listSize", listSize);
		map.put("cp", cp);

		List<AllShelterListDTO> shelterList = mapper.getAllShelters(map);

		return shelterList;
	}

	// 넘어온 페이지를 쿼리에 넣을 수 있게 가공하는 메서드
	public int changeCurrentPage(int cp, int listSize) {
		if (cp == 0) {
			cp = 1;
		}
		cp = (cp - 1) * listSize;

		return cp;
	}
}
