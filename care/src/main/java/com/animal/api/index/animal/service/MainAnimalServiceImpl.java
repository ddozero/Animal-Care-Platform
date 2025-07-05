package com.animal.api.index.animal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.common.util.FileManager;
import com.animal.api.index.animal.mapper.MainAnimalMapper;
import com.animal.api.index.animal.model.response.MainAnimalResponseDTO;

@Service
@Primary
public class MainAnimalServiceImpl implements MainAnimalService {

	private final MainAnimalMapper animalMapper;
	private final FileManager fileManager;
	
	@Autowired
	public MainAnimalServiceImpl(MainAnimalMapper animalMapper, FileManager fileManager) {
		this.animalMapper = animalMapper;
		this.fileManager = fileManager;
	}
	
	@Override
	public List<MainAnimalResponseDTO> getRecentAnimals(int limit) {
		List<MainAnimalResponseDTO> list = animalMapper.selectRecentAnimals(limit);
		
		for(MainAnimalResponseDTO dto : list) {
			List<String> imagePaths = fileManager.getImagePath("animals", dto.getIdx());
			
	        if (imagePaths != null && !imagePaths.isEmpty() && imagePaths.get(0) != null) {
	            dto.setThumbnailPath(imagePaths.get(0));  // 대표 이미지 = 첫 번째 이미지
	        }
		}
			
		return list;
	}

}
