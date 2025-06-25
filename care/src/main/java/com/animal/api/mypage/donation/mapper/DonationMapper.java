package com.animal.api.mypage.donation.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.animal.api.mypage.donation.model.response.DonationListResponseDTO;

@Mapper
public interface DonationMapper {
	// 기부한 기부글 목록 조회
	List<DonationListResponseDTO> findDonationListByUserIdx(@Param("userIdx") int userIdx);
}
