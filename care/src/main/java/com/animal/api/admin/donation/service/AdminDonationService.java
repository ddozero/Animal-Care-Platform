package com.animal.api.admin.donation.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.animal.api.admin.donation.model.request.AdminAddDonationRequestDTO;
import com.animal.api.admin.donation.model.request.AdminDonationSearchRequestDTO;
import com.animal.api.admin.donation.model.response.AdminAllDonationResponseDTO;
import com.animal.api.admin.donation.model.response.AdminDonationUserResponseDTO;
import com.animal.api.support.model.response.UserNoticeResponseDTO;

public interface AdminDonationService {
	
	public static int POST_OK = 1;
	public static int UPDATE_OK = 2;
	public static int DELETE_OK = 3;
	public static int ERROR = -1;
	
	public List<AdminAllDonationResponseDTO> getAdminDonationList(int listSize, int cp);

	public List<AdminAllDonationResponseDTO> searchAdminDonation(int listSize, int cp, String name, String status);

	public AdminAllDonationResponseDTO getAdminDonationDetail(int idx, int userIdx);
	
	//후원자 목록 조회
	public List<AdminDonationUserResponseDTO> getAdminDonationUser(int listSize, int cp, int idx);
	
	public int addAdminDonation(AdminAddDonationRequestDTO dto, int userIdx);
	
	public int uploadDonationFiles(MultipartFile[] files, int idx);
}
