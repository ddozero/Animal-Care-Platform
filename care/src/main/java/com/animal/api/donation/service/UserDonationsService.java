package com.animal.api.donation.service;

import java.util.List;

import com.animal.api.donation.model.request.DonationCommentRequestDTO;
import com.animal.api.donation.model.response.AllDonationCommentsResponseDTO;
import com.animal.api.donation.model.response.AllDonationListResponseDTO;
import com.animal.api.donation.model.response.AllDonationUserListResponseDTO;
import com.animal.api.donation.model.response.DonationDetailResponseDTO;

public interface UserDonationsService {
	public List<AllDonationListResponseDTO> getAllDonations(int listSize, int cp);

	public DonationDetailResponseDTO getDonationDetail(int idx);

	public List<AllDonationCommentsResponseDTO> getDonationComments(int idx, int listSize, int cp);

	public List<AllDonationUserListResponseDTO> getDonationUserLists(int idx, int listSize, int cp);

	public int addDonationComment(DonationCommentRequestDTO dto);
}
