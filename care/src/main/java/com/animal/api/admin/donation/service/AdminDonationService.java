package com.animal.api.admin.donation.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.animal.api.admin.donation.model.request.AdminAddDonationRequestDTO;
import com.animal.api.admin.donation.model.request.AdminDonationSearchRequestDTO;
import com.animal.api.admin.donation.model.request.AdminUpdateRequestDTO;
import com.animal.api.admin.donation.model.response.AdminAllDonationResponseDTO;
import com.animal.api.admin.donation.model.response.AdminDonationUserResponseDTO;
import com.animal.api.common.model.PageInformationDTO;
import com.animal.api.support.model.response.UserNoticeResponseDTO;

public interface AdminDonationService {
	
	public static int POST_OK = 1;
	public static int UPDATE_OK = 2;
	public static int UPLOAD_OK = 3;
	public static int DELETE_OK = 4;
	public static int ERROR = -1;
	public static int DONATION_NOT_FOUND = -2;
	
	public List<AdminAllDonationResponseDTO> getAdminDonationList(int cp);
	
	public PageInformationDTO getAdminDonationPage(int cp); //지원사업 목록 페이징 구현

	public List<AdminAllDonationResponseDTO> searchAdminDonation(int cp, String name, String status);
	
	public PageInformationDTO getSearchAdminDonationPage(int cp, String name, String status); //지원사업 검색 목록 페이징 구현

	public AdminAllDonationResponseDTO getAdminDonationDetail(int idx, int userIdx);
	
	public List<AdminDonationUserResponseDTO> getAdminDonationUser(int cp, int idx); 	//후원자 목록 조회
	
	public PageInformationDTO getAdminDonationUserPage(int cp); //지원사업 후원자 목록 페이징 구현
	
	public int addAdminDonation(AdminAddDonationRequestDTO dto, int userIdx);
	
	public int uploadDonationFiles(MultipartFile[] files, int idx); //파일 업로드
	
	public int updateAdminDonation(AdminUpdateRequestDTO dto, int idx);
	
	public int deleteAdminDonation(int idx);
	
}
