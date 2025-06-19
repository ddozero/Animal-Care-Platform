package com.animal.api.donation.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.animal.api.donation.model.response.AllDonationCommentsResponseDTO;
import com.animal.api.donation.model.response.AllDonationListResponseDTO;
import com.animal.api.donation.model.response.AllDonationUserListResponseDTO;
import com.animal.api.donation.model.response.DonationDetailResponseDTO;

@Mapper
public interface UserDonationsMapper {
	public List<AllDonationListResponseDTO> getAllDonations(Map map);

	public DonationDetailResponseDTO getDonationDetail(int idx);

	public List<AllDonationCommentsResponseDTO> getDonationComments(Map map);

	public List<AllDonationUserListResponseDTO> getDonationUserLists(Map map);
}
