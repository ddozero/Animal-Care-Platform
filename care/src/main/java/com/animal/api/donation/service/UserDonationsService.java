package com.animal.api.donation.service;

import java.util.List;

import com.animal.api.donation.model.response.AllDonationListResponseDTO;
import com.animal.api.donation.model.response.DonationDetailResponseDTO;

public interface UserDonationsService {
	public List<AllDonationListResponseDTO> getAllDonations(int listSize, int cp);
	public DonationDetailResponseDTO getDonationDetail(int idx);
}
