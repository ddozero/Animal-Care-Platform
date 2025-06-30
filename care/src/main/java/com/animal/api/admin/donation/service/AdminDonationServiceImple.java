package com.animal.api.admin.donation.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.animal.api.admin.donation.mapper.AdminDonationMapper;
import com.animal.api.admin.donation.model.request.AdminAddDonationRequestDTO;
import com.animal.api.admin.donation.model.request.AdminDonationSearchRequestDTO;
import com.animal.api.admin.donation.model.request.AdminUpdateRequestDTO;
import com.animal.api.admin.donation.model.response.AdminAllDonationResponseDTO;
import com.animal.api.admin.donation.model.response.AdminDonationUserResponseDTO;
import com.animal.api.common.model.PageInformationDTO;
import com.animal.api.common.util.FileManager;

@Service
@Primary
public class AdminDonationServiceImple implements AdminDonationService {

	@Autowired
	private AdminDonationMapper mapper;

	@Autowired
	private FileManager fileManager;

	private int listSize = 5;
	private int pageSize = 5;

	@Override
	public List<AdminAllDonationResponseDTO> getAdminDonationList(int cp) {

		Map<String, Integer> map = new HashMap<String, Integer>();

		map.put("listSize", listSize);
		map.put("cp", cp);

		List<AdminAllDonationResponseDTO> donationList = mapper.getAdminDonationList(map);

		return donationList;
	}

	@Override
	public PageInformationDTO getAdminDonationPage(int cp) {
		if (cp == 0) {
			cp = 1;
		}
		int totalCnt = mapper.getAdminDonationTotalCnt();
		PageInformationDTO page = new PageInformationDTO(totalCnt, listSize, pageSize, cp);
		return page;
	}

	@Override
	public List<AdminAllDonationResponseDTO> searchAdminDonation(int cp, String name, String status) {

		AdminDonationSearchRequestDTO dto = new AdminDonationSearchRequestDTO(listSize, cp, name, status);
		List<AdminAllDonationResponseDTO> donationList = mapper.searchAdminDonation(dto);

		return donationList;
	}

	@Override
	public PageInformationDTO getSearchAdminDonationPage(int cp, String name, String status) {

		AdminDonationSearchRequestDTO dto = new AdminDonationSearchRequestDTO(listSize, cp, name, status);

		if (cp == 0) {
			cp = 1;
		}
		int totalCnt = mapper.getSearchAdminDonationTotalCnt(dto);
		PageInformationDTO page = new PageInformationDTO(totalCnt, listSize, pageSize, cp);

		return page;
	}

	@Override
	public AdminAllDonationResponseDTO getAdminDonationDetail(int idx, int userIdx) {

		AdminAllDonationResponseDTO dto = mapper.getAdminDonationDetail(idx);
		if (dto != null) {
			List<String> imagePaths = fileManager.getImagePath("donations", dto.getIdx());
			if (imagePaths != null || imagePaths.size() != 0) { // 이미지 경로 가져오기
				dto.setImagePaths(imagePaths);
			}
		}
		return dto;
	}

	@Override
	public List<AdminDonationUserResponseDTO> getAdminDonationUser(int cp, int idx) {

		Map<String, Integer> map = new HashMap<String, Integer>();

		map.put("listSize", listSize);
		map.put("cp", cp);
		map.put("idx", idx);

		List<AdminDonationUserResponseDTO> userList = mapper.getAdminDonationUser(map);

		return userList;
	}
	
	@Override
	public PageInformationDTO getAdminDonationUserPage(int cp) {
		
		if (cp == 0) {
			cp = 1;
		}
		int totalCnt = mapper.getAdminDonationUserTotalCnt();
		PageInformationDTO page = new PageInformationDTO(totalCnt, listSize, pageSize, cp);
		return page;
	}

	@Override
	public int addAdminDonation(AdminAddDonationRequestDTO dto, int userIdx) {

		dto.setUserIdx(userIdx);
		int result = mapper.addAdminDonation(dto);

		if (result > 0) {
			return POST_OK;
		} else {
			return ERROR;
		}
	}

	@Override
	public int uploadDonationFiles(MultipartFile[] files, int idx) {

		Integer donationIdx = mapper.checkDonationIdx(idx);
		if (donationIdx == null || donationIdx == 0) {
			return DONATION_NOT_FOUND;
		}

		boolean result = fileManager.uploadFiles("donations", idx, files);

		if (result) {
			return UPLOAD_OK;
		} else {
			return ERROR;
		}
	}

	@Override
	public int updateAdminDonation(AdminUpdateRequestDTO dto, int idx) {

		Integer donationIdx = mapper.checkDonationIdx(idx);
		if (donationIdx == null || donationIdx == 0) {
			return DONATION_NOT_FOUND;
		}

		int result = mapper.updateAdminDonation(dto);

		if (result == 1) {
			return UPDATE_OK;
		} else {
			return ERROR;
		}
	}

	@Override
	public int deleteAdminDonation(int idx) {

		Integer donationIdx = mapper.checkDonationIdx(idx);
		if (donationIdx == null || donationIdx == 0) {
			return DONATION_NOT_FOUND;
		}

		int result = mapper.deleteAdminDonation(idx);

		if (result == 1) {
			fileManager.deleteFolder("donations", idx);
			return DELETE_OK;
		} else {
			return ERROR;
		}
	}

}
