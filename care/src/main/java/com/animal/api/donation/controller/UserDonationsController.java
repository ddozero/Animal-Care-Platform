package com.animal.api.donation.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.auth.model.response.LoginResponseDTO;
import com.animal.api.common.model.ErrorResponseDTO;
import com.animal.api.common.model.OkPageResponseDTO;
import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.common.model.PageInformationDTO;
import com.animal.api.donation.model.request.DonationCommentRequestDTO;
import com.animal.api.donation.model.request.DonationCommentUpdateRequestDTO;
import com.animal.api.donation.model.request.DonationRequestDTO;
import com.animal.api.donation.model.response.AllDonationCommentsResponseDTO;
import com.animal.api.donation.model.response.AllDonationListResponseDTO;
import com.animal.api.donation.model.response.AllDonationUserListResponseDTO;
import com.animal.api.donation.model.response.DonationDetailResponseDTO;
import com.animal.api.donation.service.UserDonationsService;

/**
 * 사용자 기준 기부 관련 컨트롤러 클래스
 * 
 * @author consgary
 * @since 2025.06.22
 * @see com.animal.api.donation.model.response.AllDonationListResponseDTO
 * @see com.animal.api.donation.model.response.DonationDetailResponseDTO
 * @see com.animal.api.donation.model.response.AllDonationCommentsResponseDTO
 * @see com.animal.api.donation.model.response.AllDonationUserListResponseDTO
 * @see com.animal.api.donation.model.request.DonationCommentRequestDTO
 * @see com.animal.api.donation.model.request.DonationCommentUpdateRequestDTO
 * @see com.animal.api.donation.model.request.DonationCommentDeleteRequestDTO
 * @see com.animal.api.donation.model.request.DonationRequestDTO
 */
@RestController
@RequestMapping("/api/donations")
public class UserDonationsController {

	@Autowired
	private UserDonationsService service;

	/**
	 * 기부 전체 조회
	 * 
	 * @param cp 현재 페이지
	 * @return 기부 전체 리스트
	 */
	@GetMapping
	public ResponseEntity<?> getAllDonationsLists(@RequestParam(value = "cp", defaultValue = "0") int cp) {
		List<AllDonationListResponseDTO> donationList = service.getAllDonations(cp);
		PageInformationDTO pageInfo = service.getAllDonationsPageInfo(cp);

		if (donationList == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "잘못된 요청"));
		} else if (donationList.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "데이터가 존재하지않음"));
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(new OkPageResponseDTO<List<AllDonationListResponseDTO>>(
					200, "기부 전체 조회 성공", donationList, pageInfo));
		}
	}

	/**
	 * 기부 상세 정보 조회
	 * 
	 * @param idx 기부번호
	 * @return 기부 상세 정보
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
		List<AllDonationCommentsResponseDTO> commentList = service.getDonationComments(idx, cp);
		PageInformationDTO pageInfo = service.getDonationCommentsPageInfo(idx, cp);

		if (commentList == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "잘못된 요청"));
		} else if (commentList.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "응원 댓글 데이터가 존재하지않음"));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkPageResponseDTO<List<AllDonationCommentsResponseDTO>>(200, "응원 댓글 전체 조회 성공",
							commentList, pageInfo));
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
		List<AllDonationUserListResponseDTO> userList = service.getDonationUserLists(idx, cp);
		PageInformationDTO pageInfo = service.getDonationUserListsPageInfo(idx, cp);

		if (userList == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "잘못된 요청"));
		} else if (userList.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "기부내역 데이터가 존재하지않음"));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkPageResponseDTO<List<AllDonationUserListResponseDTO>>(200, "기부내역 전체 조회 성공", userList,
							pageInfo));
		}
	}

	/**
	 * 응원 댓글 등록
	 * 
	 * @param idx 기부번호
	 * @param dto 댓글 폼
	 * @return 댓글 등록 성공,실패 메세지
	 */
	@PostMapping("/{idx}/comments")
	public ResponseEntity<?> addDonationComment(@PathVariable int idx, @RequestBody DonationCommentRequestDTO dto,
			HttpSession session) {

		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");

		if (loginUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}
		dto.setUserIdx(loginUser.getIdx());
		Map resultMap = service.addDonationComment(dto);

		if ((int) resultMap.get("result") == service.POST_SUCCESS) {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new OkResponseDTO<Void>(201, (String) resultMap.get("msg"), null));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorResponseDTO(400, (String) resultMap.get("msg")));
		}
	}

	/**
	 * 응원 댓글 수정
	 * 
	 * @param idx     기부 번호
	 * @param dcIdx   댓글 번호
	 * @param dto     댓글 수정 폼
	 * @param session 로그인 검증용
	 * @return 댓글 수정 성공,실패 메세지
	 */
	@PutMapping("{idx}/comments/{dcIdx}")
	public ResponseEntity<?> updateDonationComment(@PathVariable int idx, @PathVariable int dcIdx,
			@RequestBody DonationCommentUpdateRequestDTO dto, HttpSession session) {
		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");

		if (loginUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}
		dto.setUserIdx(loginUser.getIdx());
		Map resultMap = service.updateDonationComment(dto);

		if ((int) resultMap.get("result") == service.POST_SUCCESS) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkResponseDTO<Void>(200, (String) resultMap.get("msg"), null));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorResponseDTO(400, (String) resultMap.get("msg")));
		}
	}

	/**
	 * 응원 댓글 삭제
	 * 
	 * @param donationIdx        기부 번호
	 * @param donationCommentIdx 응원 댓글 번호
	 * @param dto                댓글 삭제를 위한 정보
	 * @param session            로그인 검증용
	 * @return 댓글 삭제,성공 메세지
	 */
	@DeleteMapping("{donationIdx}/comments/{donationCommentIdx}")
	public ResponseEntity<?> deleteDonationComment(@PathVariable int donationIdx, @PathVariable int donationCommentIdx,
			HttpSession session) {
		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");
		if (loginUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		Map resultMap = service.deleteDonationComment(donationCommentIdx, loginUser.getIdx());

		if ((int) resultMap.get("result") == service.DELETE_SUCCESS) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkResponseDTO<Void>(200, (String) resultMap.get("msg"), null));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorResponseDTO(400, (String) resultMap.get("msg")));
		}
	}

	/**
	 * 회원 보유 포인트 조회
	 * 
	 * @param donationIdx 기부번호
	 * @param userIdx     회원번호
	 * @param session     로그인 검증용 세션
	 * @return 회원 보유 포인트
	 */
	@GetMapping("/{donationIdx}/users/{userIdx}")
	public ResponseEntity<?> getDonationUserPoint(@PathVariable int donationIdx, @PathVariable int userIdx,
			HttpSession session) {
		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");

		if (loginUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (userIdx <= 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "유효하지 않은 회원 번호"));
		}

		int point = service.getDonationUserPoint(userIdx);

		if (point >= 0) {
			return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<>(200, "회원 보유 포인트 조회 성공", point));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "회원 정보 존재하지 않음"));
		}
	}

	/**
	 * 기부하기
	 * 
	 * @param donationIdx 기부번호
	 * @param dto         기능 수행을 위한 정보들
	 * @param session     로그인 검증용 세션
	 * @return 기부 성공 실패 메세지
	 */
	@PostMapping("/{donationIdx}/users")
	public ResponseEntity<?> addDonation(@PathVariable int donationIdx, @Valid @RequestBody DonationRequestDTO dto,
			HttpSession session) {
		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");

		if (loginUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}
		dto.setUserIdx(loginUser.getIdx());
		Map resultMap = service.addDonation(dto, loginUser.getIdx());

		if ((int) resultMap.get("result") == service.POST_SUCCESS) {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new OkResponseDTO<Void>(201, (String) resultMap.get("msg"), null));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorResponseDTO(400, (String) resultMap.get("msg")));
		}

	}
}
