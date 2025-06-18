package com.animal.api.donation.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.animal.api.donation.dao.UserDonationsMapper;
import com.animal.api.donation.model.response.AllDonationListResponseDTO;

@Service
public class UserDonationsServiceImple implements UserDonationsService {
	
	@Autowired
	private UserDonationsMapper mapper;
	
	
	@Override
	public List<AllDonationListResponseDTO> getAllDonations(int listSize, int cp) {
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("listSize", listSize);
		map.put("cp", cp);
		List<AllDonationListResponseDTO> donationList=mapper.getAllDonations(map);
		
		return donationList;
	}
}
