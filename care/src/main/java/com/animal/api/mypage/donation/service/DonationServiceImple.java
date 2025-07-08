package com.animal.api.mypage.donation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.common.util.FileManager;
import com.animal.api.mypage.donation.mapper.DonationMapper;
import com.animal.api.mypage.donation.model.response.DonationListResponseDTO;

@Service
@Primary
public class DonationServiceImple implements DonationService {

	@Autowired
	private DonationMapper donationMapper;

	@Autowired
	private FileManager fileManager;

	@Override
	public List<DonationListResponseDTO> getMyDonationList(int userIdx) {
		// 게시글 고유 donationIdx 기반으로 이미지 경로 가져오기
		List<DonationListResponseDTO> list = donationMapper.findDonationListByUserIdx(userIdx);

		for (DonationListResponseDTO dto : list) {
			List<String> imagePaths = fileManager.getImagePath("donations", dto.getDonationIdx());
			// 경로가 비어있지 않으면 첫 번째 이미지로 대표 이미지 지정
			if (imagePaths != null && !imagePaths.isEmpty()) {
				dto.setImagePath(imagePaths.get(0));
			}
		}

		return list;
	}
}
