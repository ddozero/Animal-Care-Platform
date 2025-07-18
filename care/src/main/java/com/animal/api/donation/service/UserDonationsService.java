package com.animal.api.donation.service;

import java.util.List;
import java.util.Map;

import com.animal.api.common.model.PageInformationDTO;
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
	static final int INSUFFICIENT_POINT = 7;
	static int ERROR = -1;

	public List<AllDonationListResponseDTO> getAllDonations(int cp);

	public PageInformationDTO getAllDonationsPageInfo(int cp);

	public DonationDetailResponseDTO getDonationDetail(int idx);

	public List<AllDonationCommentsResponseDTO> getDonationComments(int idx, int cp);

	public PageInformationDTO getDonationCommentsPageInfo(int idx, int cp);

	public List<AllDonationUserListResponseDTO> getDonationUserLists(int idx, int cp);

	public PageInformationDTO getDonationUserListsPageInfo(int idx, int cp);

	public Map addDonationComment(DonationCommentRequestDTO dto);

	public Map updateDonationComment(DonationCommentUpdateRequestDTO dto);

	public Map deleteDonationComment(int idx, int userIdx);

	public int getDonationUserPoint(int userIdx);

	public Map addDonation(DonationRequestDTO dto, int userIdx);
}
