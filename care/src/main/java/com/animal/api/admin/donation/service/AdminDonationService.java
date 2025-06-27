package com.animal.api.admin.donation.service;

import java.util.List;
import java.util.Map;

import com.animal.api.admin.donation.model.request.AdminDonationSearchRequestDTO;
import com.animal.api.admin.donation.model.response.AdminAllDonationResponseDTO;
import com.animal.api.admin.donation.model.response.AdminDonationUserResponseDTO;
import com.animal.api.support.model.response.UserNoticeResponseDTO;

public interface AdminDonationService {
	
	public List<AdminAllDonationResponseDTO> getAdminDonationList(int listSize, int cp);

	public List<AdminAllDonationResponseDTO> searchAdminDonation(int listSize, int cp, String name, String status);

	public AdminAllDonationResponseDTO getAdminDonationDetail(int idx, int userIdx);
	
	//후원자 목록 조회
	public List<AdminDonationUserResponseDTO> getAdminDonationUser(int listSize, int cp, int idx);
}
