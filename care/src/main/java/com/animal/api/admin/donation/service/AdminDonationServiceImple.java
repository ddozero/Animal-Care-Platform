package com.animal.api.admin.donation.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.admin.donation.mapper.AdminDonationMapper;
import com.animal.api.admin.donation.model.response.AdminAllDonationResponseDTO;
import com.animal.api.common.util.FileManager;

@Service
@Primary
public class AdminDonationServiceImple implements AdminDonationService {

	@Autowired
	private AdminDonationMapper mapper;

	@Autowired
	private FileManager fileManager;

	@Override
	public List<AdminAllDonationResponseDTO> getAdminDonationList(int listSize, int cp, int idx) {

		Map<String, Integer> map = new HashMap<String, Integer>();

		map.put("listSize", listSize);
		map.put("cp", cp);
		map.put("idx", idx);

		List<AdminAllDonationResponseDTO> donationList = mapper.getAdminDonationList(map);
		
		return donationList;
	}

}
