package com.animal.api.index.volunteer.service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.common.util.FileManager;
import com.animal.api.index.volunteer.mapper.MainVolunteerMapper;
import com.animal.api.index.volunteer.model.response.MainVolunteerCardResponseDTO;
import com.animal.api.index.volunteer.model.response.MainVolunteerSummaryDTO;

@Service
@Primary
public class MainVolunteerServiceImpl implements MainVolunteerService {
	
	private final MainVolunteerMapper volunteerMapper;
	private final FileManager fileManager;
	
	@Autowired
	public MainVolunteerServiceImpl(MainVolunteerMapper volunteerMapper, FileManager fileManager) {
		this.volunteerMapper = volunteerMapper;
		this.fileManager = fileManager;
	}
	
	@Override
	public List<MainVolunteerCardResponseDTO> getRecentVolunteers() {
	    List<MainVolunteerSummaryDTO> volunteers = volunteerMapper.selectRecentVolunteers();

	    return volunteers.stream().map(v -> {
	        MainVolunteerCardResponseDTO dto = new MainVolunteerCardResponseDTO();
	        dto.setIdx(v.getIdx());
	        dto.setTitle(v.getTitle());
	        dto.setLocation(v.getLocation());

	        // 날짜 포맷
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd(E)", Locale.KOREAN);
	        dto.setFormattedDate(v.getVolunteerDate().format(formatter));

	        // 이미지 경로
	        List<String> images = fileManager.getImagePath("volunteers", v.getIdx());
	        dto.setThumbnailPath((images != null && !images.isEmpty()) ? images.get(0) : null);

	        return dto;
	    }).collect(Collectors.toList());
	} 
}
