package com.animal.api.donation.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.animal.api.donation.model.response.AllDonationListResponseDTO;

@Mapper
public interface UserDonationsMapper {

	public List<AllDonationListResponseDTO> getAllDonations(Map map);
}
