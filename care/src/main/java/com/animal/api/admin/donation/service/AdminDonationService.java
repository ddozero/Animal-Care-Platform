package com.animal.api.admin.donation.service;

import java.util.List;
import java.util.Map;

import com.animal.api.admin.donation.model.request.AdminDonationSearchRequestDTO;
import com.animal.api.admin.donation.model.response.AdminAllDonationResponseDTO;
import com.animal.api.support.model.response.UserNoticeResponseDTO;

public interface AdminDonationService {
	
	public List<AdminAllDonationResponseDTO> getAdminDonationList(int listSize, int cp, int idx);
	
	public List<AdminAllDonationResponseDTO> searchAdminDonation(int listSize, int cp, String name, String status);
}
