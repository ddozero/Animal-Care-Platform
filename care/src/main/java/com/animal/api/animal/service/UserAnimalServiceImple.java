package com.animal.api.animal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.animal.dao.UserAnimalMapper;
import com.animal.api.animal.model.response.AllAnimalListResponseDTO;

@Service
@Primary
public class UserAnimalServiceImple implements UserAnimalService {

	@Autowired
	private UserAnimalMapper mapper;

	@Override
	public List<AllAnimalListResponseDTO> getAllAnimals(int listSize, int cp) {
		// 현재 페이지를 페이징 쿼리에 맞게 가공
		if (cp == 0) {
			cp = 1;
		}
		cp = (cp - 1) * listSize;

		Map<String, Integer> map = new HashMap<String, Integer>();

		map.put("listSize", listSize);
		map.put("cp", cp);

		List<AllAnimalListResponseDTO> animalList = mapper.getAllAnimals(map);

		return animalList;
	}
}
