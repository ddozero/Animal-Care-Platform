package com.animal.api.donation.service;

import java.util.List;
import java.util.Map;

import com.animal.api.donation.model.request.DonationCommentDeleteRequestDTO;
import com.animal.api.donation.model.request.DonationCommentRequestDTO;
import com.animal.api.donation.model.request.DonationCommentUpdateRequestDTO;
import com.animal.api.donation.model.request.DonationRequestDTO;
import com.animal.api.donation.model.response.AllDonationCommentsResponseDTO;
import com.animal.api.donation.model.response.AllDonationListResponseDTO;
import com.animal.api.donation.model.response.AllDonationUserListResponseDTO;
import com.animal.api.donation.model.response.DonationDetailResponseDTO;

public interface UserDonationsService {
	static int POST_SUCCESS = 1;
	static int USER_NOT_FOUND = 2;
	static int DONATION_NOT_FOUND = 3;
	static int COMMENT_CONTENT_EMPTY = 4;
	static int COMMENT_NOT_FOUND = 5;
	static int DELETE_SUCCESS = 6;
	public static final int INSUFFICIENT_POINT = 7;
	static int ERROR = -1;

	public List<AllDonationListResponseDTO> getAllDonations(int listSize, int cp);

	public DonationDetailResponseDTO getDonationDetail(int idx);

	public List<AllDonationCommentsResponseDTO> getDonationComments(int idx, int listSize, int cp);

	public List<AllDonationUserListResponseDTO> getDonationUserLists(int idx, int listSize, int cp);

	public Map addDonationComment(DonationCommentRequestDTO dto);

	public Map updateDonationComment(DonationCommentUpdateRequestDTO dto);

	public Map deleteDonationComment(DonationCommentDeleteRequestDTO dto);

	public int getDonationUserPoint(int idx);

	public int addDonation(DonationRequestDTO dto,int userPoint);
}
