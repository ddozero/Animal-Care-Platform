package com.animal.api.admin.donation.service;

import java.util.List;
import java.util.Map;

import com.animal.api.admin.donation.model.response.AdminAllDonationResponseDTO;

public interface AdminDonationService {
	
	public List<AdminAllDonationResponseDTO> getAdminDonationList(int listSize, int cp, int idx);

}
