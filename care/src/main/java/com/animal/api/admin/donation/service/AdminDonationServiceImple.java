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
import com.animal.api.admin.donation.model.response.AdminAllDonationResponseDTO;
import com.animal.api.admin.donation.model.response.AdminDonationUserResponseDTO;
import com.animal.api.common.util.FileManager;

@Service
@Primary
public class AdminDonationServiceImple implements AdminDonationService {

	@Autowired
	private AdminDonationMapper mapper;

	@Autowired
	private FileManager fileManager;

	@Override
	public List<AdminAllDonationResponseDTO> getAdminDonationList(int listSize, int cp) {

		Map<String, Integer> map = new HashMap<String, Integer>();

		map.put("listSize", listSize);
		map.put("cp", cp);

		List<AdminAllDonationResponseDTO> donationList = mapper.getAdminDonationList(map);

		return donationList;
	}

	@Override
	public List<AdminAllDonationResponseDTO> searchAdminDonation(int listSize, int cp, String name, String status) {

		AdminDonationSearchRequestDTO dto = new AdminDonationSearchRequestDTO(listSize, cp, name, status);
		List<AdminAllDonationResponseDTO> donationList = mapper.searchAdminDonation(dto);

		return donationList;
	}

	@Override
	public AdminAllDonationResponseDTO getAdminDonationDetail(int idx, int userIdx) {

		AdminAllDonationResponseDTO dto = mapper.getAdminDonationDetail(idx);
		if (dto != null) {
			List<String> imagePaths = fileManager.getImagePath("donations", dto.getIdx());
			if (imagePaths != null && !imagePaths.isEmpty()) { // 이미지 경로 가져오기
				dto.setImagePath(imagePaths.get(0));
			}
		}
		return dto;
	}

	@Override
	public List<AdminDonationUserResponseDTO> getAdminDonationUser(int listSize, int cp, int idx) {

		Map<String, Integer> map = new HashMap<String, Integer>();

		map.put("listSize", listSize);
		map.put("cp", cp);
		map.put("idx", idx);

		List<AdminDonationUserResponseDTO> userList = mapper.getAdminDonationUser(map);

		return userList;
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
		
		boolean result = fileManager.uploadFiles("donations", idx, files);
		
		if (result) {
			return UPLOAD_OK;
		} else {
			return ERROR;
		}
	}

}
