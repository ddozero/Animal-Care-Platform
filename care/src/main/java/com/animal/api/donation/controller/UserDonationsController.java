package com.animal.api.donation.controller;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.common.model.ErrorResponseDTO;
import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.donation.model.request.DonationCommentRequestDTO;
import com.animal.api.donation.model.response.AllDonationCommentsResponseDTO;
import com.animal.api.donation.model.response.AllDonationListResponseDTO;
import com.animal.api.donation.model.response.AllDonationUserListResponseDTO;
import com.animal.api.donation.model.response.DonationDetailResponseDTO;
import com.animal.api.donation.service.UserDonationsService;

/**
 * @author consgary
 * @since 2025.06.19
 * @see com.animal.api.donation.model.response.AllDonationListResponseDTO
 * @see com.animal.api.donation.model.response.DonationDetailResponseDTO
 * @see com.animal.api.donation.model.response.AllDonationCommentsResponseDTO
 * @see com.animal.api.donation.model.response.AllDonationUserListResponseDTO
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

	/**
	 * 응원 댓글 전체 조회
	 * 
	 * @param idx 기부번호
	 * @param cp  페이지 번호
	 * @return 응원 댓글 전체 리스트
	 */
	@GetMapping("/{idx}/comments")
	public ResponseEntity<?> getDonationComments(@PathVariable int idx,
			@RequestParam(value = "cp", defaultValue = "0") int cp) {
		int listSize = 3;
		List<AllDonationCommentsResponseDTO> commentList = service.getDonationComments(idx, listSize, cp);

		if (commentList == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "잘못된 요청"));
		} else if (commentList.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "응원 댓글 데이터가 존재하지않음"));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkResponseDTO<List<AllDonationCommentsResponseDTO>>(200, "응원 댓글 전체 조회 성공", commentList));
		}

	}

	/**
	 * 기부 내역 전체 조회
	 * 
	 * @param idx 기부 번호
	 * @param cp  페이지 번호
	 * @return 기부 내역 전체 리스트
	 */
	@GetMapping("/{idx}/userLists")
	public ResponseEntity<?> getDonationUserLists(@PathVariable int idx,
			@RequestParam(value = "cp", defaultValue = "0") int cp) {
		int listSize = 3;
		List<AllDonationUserListResponseDTO> userList = service.getDonationUserLists(idx, listSize, cp);

		if (userList == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "잘못된 요청"));
		} else if (userList.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "기부내역 데이터가 존재하지않음"));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkResponseDTO<List<AllDonationUserListResponseDTO>>(200, "기부내역 전체 조회 성공", userList));
		}
	}

	@PostMapping("/{idx}/comments")
	public ResponseEntity<?> addDonationComment(@PathVariable int idx, @RequestBody DonationCommentRequestDTO dto) {

		Map resultMap = service.addDonationComment(dto);

		if ((int) resultMap.get("result") == service.POST_SUCCESS) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkResponseDTO<Void>(200, (String) resultMap.get("msg"), null));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorResponseDTO(400, (String) resultMap.get("msg")));
		}
	}
}
