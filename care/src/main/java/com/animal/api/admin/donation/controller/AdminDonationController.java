package com.animal.api.admin.donation.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.animal.api.admin.donation.model.request.AdminAddDonationRequestDTO;
import com.animal.api.admin.donation.model.response.AdminAllDonationResponseDTO;
import com.animal.api.admin.donation.model.response.AdminDonationUserResponseDTO;
import com.animal.api.admin.donation.service.AdminDonationService;
import com.animal.api.admin.shelter.service.AdminShelterService;
import com.animal.api.animal.model.response.AllAnimalListResponseDTO;
import com.animal.api.auth.model.response.LoginResponseDTO;
import com.animal.api.common.model.ErrorResponseDTO;
import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.management.shelter.model.response.AllManageShelterResponseDTO;
import com.animal.api.support.model.response.UserNoticeResponseDTO;

/**
 * 사이트 관리자 페이지의 지원사업 관련 기능 클래스
 * 
 * @author ddozero
 * @since 2025-06-27
 * @see com.animal.api.admin.donation.model.response.AdminAllDonationResponseDTO
 * @see com.animal.api.admin.donation.model.response.AdminDonationUserResponseDTO
 *
 */

@RestController
@RequestMapping("/api/admin/donations")

public class AdminDonationController {

	@Autowired
	private AdminDonationService adminDonationService;

	/**
	 * 사이트 관리 페이지 지원사업 목록 조회 메서드
	 * 
	 * @param cp      현재 페이지
	 * @param name    지원사업명
	 * @param status  지원사업 상태
	 * @param session 로그인 검증 세션
	 * 
	 * @return 조회된 지원사업 목록
	 */
	@GetMapping
	public ResponseEntity<?> getAdminDonationList(@RequestParam(value = "cp", defaultValue = "0") int cp,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "status", required = false) String status, HttpSession session) {

		LoginResponseDTO loginAdmin = shelterUserCheck(session); // 로그인 여부, 관리자 회원 검증

		int listSize = 5;
		if (cp == 0) {
			cp = 1;
		} else {
			cp = (cp - 1) * listSize;
		}

		List<AdminAllDonationResponseDTO> donationLists = null;

		if (name != null || status != null) {
			donationLists = adminDonationService.searchAdminDonation(listSize, cp, name, status);
		} else {
			donationLists = adminDonationService.getAdminDonationList(listSize, cp);
		}

		if (donationLists == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "잘못된 접근"));
		} else if (donationLists.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "데이터가 존재하지 않음"));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkResponseDTO<List<AdminAllDonationResponseDTO>>(200, "지원사업 목록 조회 성공", donationLists));
		}
	}

	/**
	 * 사이트 관리자 페이지 지원사업 상세정보 조회 메서드
	 * 
	 * @param idx     지원사업 번호
	 * @param session 로그인 검증 세션
	 * 
	 * @return 지원사업 상세정보 조회
	 */
	@GetMapping("/{idx}")
	public ResponseEntity<?> getAdminDonationDetail(@PathVariable int idx, HttpSession session) {

		LoginResponseDTO loginAdmin = shelterUserCheck(session); // 로그인 여부, 관리자 회원 검증
		int userIdx = loginAdmin.getIdx();

		AdminAllDonationResponseDTO dto = adminDonationService.getAdminDonationDetail(idx, userIdx);

		if (dto == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(404, "데이터가 존재하지 않음"));
		} else {
			return ResponseEntity.ok(new OkResponseDTO<AdminAllDonationResponseDTO>(200, "지원사업 상세 정보 조회 성공", dto));
		}
	}

	/**
	 * 사이트 관리자 페이지 지원사업 후원자 목록 조회 메서드
	 * 
	 * @param idx     지원사업 번호
	 * @param cp      현재 페이지
	 * @param session 로그인 검증 세션
	 * 
	 * @return 지원사업 후원자 목록 조회
	 */
	@GetMapping("{idx}/user")
	public ResponseEntity<?> getAdminDonationUser(@PathVariable int idx,
			@RequestParam(value = "cp", defaultValue = "0") int cp, HttpSession session) {

		LoginResponseDTO loginAdmin = shelterUserCheck(session); // 로그인 여부, 관리자 회원 검증

		int listSize = 5;
		if (cp == 0) {
			cp = 1;
		} else {
			cp = (cp - 1) * listSize;
		}

		List<AdminDonationUserResponseDTO> userList = adminDonationService.getAdminDonationUser(listSize, cp, idx);

		if (userList == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "잘못된 접근"));
		} else if (userList.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "후원자가 존재하지 않음"));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkResponseDTO<List<AdminDonationUserResponseDTO>>(200, "조회 성공", userList));
		}

	}

	/**
	 * 사이트 관리자 페이지 지원사업 등록 메서드
	 * 
	 * @param dto     지원사업 등록
	 * @param session 로그인 검증 세션
	 * 
	 * @return 지원사업 등록 성공 여부
	 */
	@PostMapping("/upload")
	public ResponseEntity<?> addAdminDonation(@Valid @RequestBody AdminAddDonationRequestDTO dto, HttpSession session) {

		LoginResponseDTO loginUser = shelterUserCheck(session);
		int userIdx = loginUser.getIdx(); // 로그인여부, 관리자 회원 검증

		int result = adminDonationService.addAdminDonation(dto, userIdx);

		if (result == adminDonationService.POST_OK) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new OkResponseDTO<Integer>(201, "지원사업 등록 완료", null));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "지원사업 등록 실패"));
		}
	}

	/**
	 * 사이트 관리자 페이지 지원사업 등록 파일 업로드 메서드
	 * 
	 * @param idx   지원사업 번호
	 * @param files 지원사업 파일 업로드
	 * 
	 * @return 지원사업 파일 업로드 성공 여부
	 */

	@PostMapping("/upload/{idx}")
	public ResponseEntity<?> uploadNoticeFiles(@PathVariable int idx, MultipartFile[] files) {
		int result = adminDonationService.uploadDonationFiles(files, idx);
		if (result == adminDonationService.UPLOAD_OK) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new OkResponseDTO<Void>(201, "첨부파일 업로드 성공", null));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "첨부파일 업로드 실패"));
		}
	}

	/**
	 * (공통) 로그인 및 괸리자 검증 메서드
	 * 
	 * @param session 로그인 검증 세션
	 * 
	 * @return 관리자 계정으로 로그인 확인
	 */
	public LoginResponseDTO shelterUserCheck(HttpSession session) {
		LoginResponseDTO loginAdmin = (LoginResponseDTO) session.getAttribute("loginAdmin");

		if (loginAdmin == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인 후 이용가능");
		}
		if (loginAdmin.getUserTypeIdx() != 3) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "관리자 회원만 접근 가능");
		}

		return loginAdmin;
	}

}
