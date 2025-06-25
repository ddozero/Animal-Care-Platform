package com.animal.api.mypage.donation.service;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.mypage.donation.model.response.DonationListResponseDTO;

@Service
@Primary
public class DonationServiceImple implements DonationService {

	@Override
	public List<DonationListResponseDTO> getMyDonationList(int userIdx) {
		// TODO Auto-generated method stub
		return null;
	}
}
