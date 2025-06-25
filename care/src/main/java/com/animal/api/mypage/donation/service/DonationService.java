package com.animal.api.mypage.donation.service;

import java.util.List;

import com.animal.api.mypage.donation.model.response.DonationListResponseDTO;

public interface DonationService {
	List<DonationListResponseDTO> getMyDonationList(int userIdx);
}
