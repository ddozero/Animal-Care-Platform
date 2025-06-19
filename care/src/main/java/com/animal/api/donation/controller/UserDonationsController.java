package com.animal.api.donation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.common.model.ErrorResponseDTO;
import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.donation.model.response.AllDonationListResponseDTO;
import com.animal.api.donation.model.response.DonationDetailResponseDTO;
import com.animal.api.donation.service.UserDonationsService;

/**
 * @author consgary
 * @since 2025.06.19
 * @see com.animal.api.donation.model.response.AllDonationListResponseDTO
 */
@RestController
@RequestMapping("/api/donations")
public class UserDonationsController {

	@Autowired
	private UserDonationsService service;

	/**
	 * 봉사 전체 조회
	 * 
	 * @param cp 현재 페이지
	 * @return 기부 전체 리스트
	 */
	@GetMapping
	public ResponseEntity<?> getAllDonationsLists(@RequestParam(value = "cp", defaultValue = "0") int cp) {
		int listSize = 3;
		List<AllDonationListResponseDTO> donationList = service.getAllDonations(listSize, cp);

		if (donationList == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "잘못된 요청"));
		} else if (donationList.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "데이터가 존재하지않음"));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkResponseDTO<List<AllDonationListResponseDTO>>(200, "기부 전체 조회 성공", donationList));
		}
	}

	/**
	 * 봉사 상세 정보 조회
	 * 
	 * @param idx 기부번호
	 * @return 봉사 상세 정보
	 */
	@GetMapping("/{idx}")
	public ResponseEntity<?> getDonationDetail(@PathVariable int idx) {

		DonationDetailResponseDTO donationDetail = service.getDonationDetail(idx);

		if (donationDetail == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "기부 상세 데이터가 존재하지않음"));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkResponseDTO<DonationDetailResponseDTO>(200, "기부 상세 조회 성공", donationDetail));
		}

	}
}
