package com.animal.api.donation.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.animal.api.donation.model.request.DonationCommentDeleteRequestDTO;
import com.animal.api.donation.model.request.DonationCommentRequestDTO;
import com.animal.api.donation.model.request.DonationCommentUpdateRequestDTO;
import com.animal.api.donation.model.response.AllDonationCommentsResponseDTO;
import com.animal.api.donation.model.response.AllDonationListResponseDTO;
import com.animal.api.donation.model.response.AllDonationUserListResponseDTO;
import com.animal.api.donation.model.response.DonationDetailResponseDTO;
import com.animal.api.donation.model.response.UserPointResponseDTO;

@Mapper
public interface UserDonationsMapper {
	public List<AllDonationListResponseDTO> getAllDonations(Map map);

	public DonationDetailResponseDTO getDonationDetail(int idx);

	public List<AllDonationCommentsResponseDTO> getDonationComments(Map map);

	public List<AllDonationUserListResponseDTO> getDonationUserLists(Map map);

	public int addDonationComment(DonationCommentRequestDTO dto);

	public int updateDonationComment(DonationCommentUpdateRequestDTO dto);

	public int deleteDonationComment(DonationCommentDeleteRequestDTO dto);

	public UserPointResponseDTO getDonationUserPoint(int idx);
}
