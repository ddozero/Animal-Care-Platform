package com.animal.api.donation.service;

import java.util.List;
import java.util.Map;

import com.animal.api.donation.model.response.AllDonationListResponseDTO;

public interface UserDonationsService {

	public List<AllDonationListResponseDTO> getAllDonations(int listSize, int cp);
}
