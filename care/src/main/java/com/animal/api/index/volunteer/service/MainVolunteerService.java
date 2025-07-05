package com.animal.api.index.volunteer.service;

import java.util.List;

import com.animal.api.index.volunteer.model.response.MainVolunteerCardResponseDTO;

public interface MainVolunteerService {

	List<MainVolunteerCardResponseDTO> getRecentVolunteers();
}
