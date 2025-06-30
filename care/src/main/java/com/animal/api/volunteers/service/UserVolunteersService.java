package com.animal.api.volunteers.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import com.animal.api.common.model.PageInformationDTO;
import com.animal.api.volunteers.model.request.VolunteersSubmitRequestDTO;
import com.animal.api.volunteers.model.response.AllVolunteersResponseDTO;

public interface UserVolunteersService {

	public static int SUBMIT_OK = 1; // 봉사 신청 완료
	public static int SUBMIT_DUPLICATE = 2; // 중복 신청
	public static int SUBMIT_NOT_OK = 3; // 봉사 신청 불가능
	public static int SUBMIT_ERROR = -1; // 에러

	public List<AllVolunteersResponseDTO> getAllVolunteers(int cp);
	
	public PageInformationDTO getAllVolunteersPage(int cp); //사용자 봉사 페이징 구현

	public AllVolunteersResponseDTO getVolunteersDetail(int idx);

	public List<AllVolunteersResponseDTO> searchVolunteers(int cp, String title, String content,
			String location, String status, String shelter, String shelterType, LocalDate volunteerDate, String type, int time);
	
	public PageInformationDTO getSearchVolunteersPage(int cp, String title, String content, String location, String status, String shelter, String shelterType, LocalDate volunteerDate, String type, int time);

	public int submitVolunteers(VolunteersSubmitRequestDTO dto);

}
