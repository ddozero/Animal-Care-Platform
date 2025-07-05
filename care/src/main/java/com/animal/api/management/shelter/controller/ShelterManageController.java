package com.animal.api.management.shelter.controller;

import java.util.List;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.animal.api.auth.model.response.LoginResponseDTO;
import com.animal.api.common.model.ErrorResponseDTO;
import com.animal.api.common.model.OkPageResponseDTO;
import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.common.model.PageInformationDTO;
import com.animal.api.management.shelter.model.request.ManageAdoptionReplyRequestDTO;
import com.animal.api.management.shelter.model.request.ManageVolunteerReplyRequestDTO;
import com.animal.api.management.shelter.model.request.ShelterBoardRequestDTO;
import com.animal.api.management.shelter.model.request.ShelterInfoUpdateRequestDTO;
import com.animal.api.management.shelter.model.response.AllManageShelterResponseDTO;
import com.animal.api.management.shelter.model.response.ManageAdoptionReviewResponseDTO;
import com.animal.api.management.shelter.model.response.ManageVolunteerReviewResponseDTO;
import com.animal.api.management.shelter.model.response.ShelterBoardResponseDTO;
import com.animal.api.management.shelter.service.ShelterManageService;

/**
 * 보호시설 관리자 페이지의 보호시설관리 관련 컨트롤러 클래스
 * 
 * @author ddozero
 * @since 2025-06-21
 * @see com.animal.api.management.shelter.model.response.AllManageShelterDTO
 * @see com.animal.api.management.shelter.model.response.ManageAdoptionReviewResponseDTO
 * @see com.animal.api.management.shelter.model.response.ManageVolunteerReviewResponseDTO
 * @see com.animal.api.management.shelter.model.response.ShelterBoardResponseDTO
 * @see com.animal.api.management.shelter.model.request.ShelterInfoUpdateRequestDTO
 * @see com.animal.api.management.shelter.model.request.ManageVolunteerReplyRequestDTO
 * @see com.animal.api.management.shelter.model.request.ManageAdoptionReplyRequestDTO
 * 
 */
@RestController
@RequestMapping("/api/management/shelter")
public class ShelterManageController {

	@Autowired
	private ShelterManageService shelterService;

	/**
	 * 보호시설 기본정보 조회 메서드
	 * 
	 * @param session 로그인 검증 세션
	 * 
	 * @return 로그인한 보호소 기본 정보
	 */
	@GetMapping
	public ResponseEntity<?> getShelterInfo(HttpSession session) {

		LoginResponseDTO loginUser = shelterUserCheck(session);

		int userIdx = loginUser.getIdx();

		AllManageShelterResponseDTO dto = shelterService.getShelterInfo(userIdx);

		if (dto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "존재하지 않는 보호소 입니다."));
		} else {
			return ResponseEntity.ok(new OkResponseDTO<AllManageShelterResponseDTO>(200, "보호소 기본정보 조회 성공", dto));
		}
	}

	/**
	 * 보호시설 기본정보 수정 메서드
	 * 
	 * @param dto     보호시설 기본정보
	 * @param session 로그인 검증 세션
	 * 
	 * @return 수정에 따른 메세지
	 */
	@PutMapping
	public ResponseEntity<?> updateShelterInfo(@Valid @RequestBody ShelterInfoUpdateRequestDTO dto,
			HttpSession session) {

		LoginResponseDTO loginUser = shelterUserCheck(session);

		int userIdx = loginUser.getIdx();
		dto.setIdx(userIdx);
		int count = shelterService.updateShelterInfo(dto, userIdx);
		if (count > 0) {
			return ResponseEntity.ok(new OkResponseDTO<ShelterInfoUpdateRequestDTO>(200, "보호소 정보가 수정되었습니다.", dto));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "기본정보 수정 실패"));
		}
	}

	/**
	 * 보호시설 기본정보 수정 시 파일 업로드 (사업자등록증 및 이미지)
	 * 
	 * @param idx   보호시설 번호
	 * @param files 업로드 파일
	 * 
	 * @return 보호시설 info 파일 업로드 성공 여부
	 */
	@PostMapping("/upload/{idx}")
	public ResponseEntity<?> uploadShelterInfoFiles(@PathVariable int idx, MultipartFile[] files) {
		int result = shelterService.uploadShelterFile(files, idx);
		if (result == shelterService.UPLOAD_OK) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new OkResponseDTO<Void>(201, "파일 업로드가 완료되었습니다.", null));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "오류가 발생했습니다. 관리자에게 문의하세요."));
		}
	}

	/**
	 * 해당 보호시설 봉사 리뷰 조회 메서드
	 * 
	 * @param cp      현재 페이지 번호
	 * @param session 로그인 검증 세션
	 * 
	 * @return 로그인한 보호시설의 사용자 봉사 리뷰 조회
	 */
	@GetMapping("/reviews/volunteer")
	public ResponseEntity<?> getVolunteerReview(@RequestParam(value = "cp", defaultValue = "0") int cp,
			HttpSession session) {

		LoginResponseDTO loginUser = shelterUserCheck(session);

		int userIdx = loginUser.getIdx();

		List<ManageVolunteerReviewResponseDTO> reviewList = shelterService.getVolunteerReview(cp, userIdx);
		PageInformationDTO page = shelterService.getVolunteerReviewPage(cp, userIdx);

		if (reviewList == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "작성된 리뷰글이 존재하지 않습니다."));
		} else if (reviewList.size() == 0) {
			return ResponseEntity.ok(new OkPageResponseDTO<>(200, "등록된 리뷰가 없습니다", reviewList, page));
		} else {
			return ResponseEntity.ok(new OkPageResponseDTO<>(200, "리뷰 조회 성공", reviewList, page));
		}
	}

	/**
	 * 해당 보호시설 입양 리뷰 조회 메서드
	 * 
	 * @param cp      현재 페이지 번호
	 * @param session 로그인 검증 세션
	 * 
	 * @return 로그인한 보호시설의 사용자 입양 리뷰 조회
	 */
	@GetMapping("/reviews/adoption")
	public ResponseEntity<?> getAdoptionReview(@RequestParam(value = "cp", defaultValue = "0") int cp,
			HttpSession session) {

		LoginResponseDTO loginUser = shelterUserCheck(session);

		int userIdx = loginUser.getIdx();

		List<ManageAdoptionReviewResponseDTO> reviewList = shelterService.getAdoptionReview(cp, userIdx);
		PageInformationDTO page = shelterService.getAdoptionReviewPage(cp, userIdx);

		if (reviewList == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "작성된 리뷰글이 존재하지 않습니다."));
		} else if (reviewList.size() == 0) {
			return ResponseEntity.ok(new OkPageResponseDTO<>(200, "등록된 리뷰가 없습니다.", reviewList, page));
		} else {
			return ResponseEntity.ok(new OkPageResponseDTO<>(200, "리뷰 조회가 완료되었습니다.", reviewList, page));
		}

	}

	/**
	 * 해당 보호시설 봉사 리뷰 글에 대한 답글 작성 메서드
	 * 
	 * @param dto     봉사 리뷰 답변글
	 * @param session 로그인 검증 세션
	 * 
	 * @return 해당 보호시설 봉사 리뷰글 답글
	 */
	@PostMapping("/volunteerReviews/reply")
	public ResponseEntity<?> addVolunteerReviewApply(@Valid @RequestBody ManageVolunteerReplyRequestDTO dto,
			HttpSession session) {

		LoginResponseDTO loginUser = shelterUserCheck(session);
		int userIdx = loginUser.getIdx();
		dto.setUserIdx(userIdx);

		int result = shelterService.addVolunteerReviewApply(dto, userIdx, dto.getRef());

		if (result == shelterService.NOT_REVIEW) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(404, "작성된 리뷰글이 존재하지 않습니다."));
		} else if (result == shelterService.UPDATE_OK) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new OkResponseDTO<>(201, "답글 등록이 완료되었습니다.", null));
		} else if (result == shelterService.NOT_SHELTER_MANAGER) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "담당 보호소 관리자가 아니면 접근할 수 없습니다."));
		} else if (result == shelterService.NOT_ALLOWED_REPLY) {
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "답글은 한 번만 등록할 수 있습니다."));
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "오류가 발생했습니다. 관리자에게 문의하세요."));
		}
	}
	

	/**
	 * 해당 보호시설 봉사 리뷰 글에 대한 답글 수정 메서드
	 * 
	 * @param reviewIdx 봉사 리뷰 글 번호
	 * @param dto       봉사 리뷰
	 * @param session   로그인 검증 세션
	 * 
	 * @return 해당 보호시설 봉사 리뷰글 답글 수정
	 */
	@PutMapping("/volunteerReviews/reply/update/{idx}")
	public ResponseEntity<?> updateVolunteerReviewApply(@PathVariable int idx,
			@Valid @RequestBody ManageVolunteerReplyRequestDTO dto, HttpSession session) {

		LoginResponseDTO loginUser = shelterUserCheck(session);

		int userIdx = loginUser.getIdx();
		dto.setUserIdx(userIdx);
		dto.setReviewIdx(idx);

		int result = shelterService.updateVolunteerReviewApply(dto, loginUser.getIdx(), idx);

		if (result == shelterService.UPDATE_OK) {
			return ResponseEntity.ok(new OkResponseDTO<>(200, "리뷰 답글 수정이 완료되었습니다.", null));
		} else if (result == shelterService.NOT_REVIEW) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "작성된 리뷰글이 존재하지 않습니다."));
		} else if (result == shelterService.NOT_SHELTER_MANAGER) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(new ErrorResponseDTO(403, "담당 보호소 관리자가 아니면 접근할 수 없습니다."));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "오류가 발생했습니다. 관리자에게 문의하세요."));
		}
	}

	/**
	 * 해당 보호시설 봉사 리뷰 글에 대한 답글 작성 삭제 메서드
	 * 
	 * @param reviewIdx 봉사 리뷰 글 번호
	 * @param session   로그인 검증 세션
	 * 
	 * @return 해당 보호시설 봉사 리뷰글 답글 삭제
	 */
	@DeleteMapping("/volunteerReviews/reply/{idx}")
	public ResponseEntity<?> deleteVolunteerReviewApply(@PathVariable("idx") int idx, HttpSession session) {

		LoginResponseDTO loginUser = shelterUserCheck(session);

		int userIdx = loginUser.getIdx();

		int result = shelterService.deleteVolunteerReviewApply(userIdx, idx);

		if (result == shelterService.DELETE_OK) {
			return ResponseEntity.ok(new OkResponseDTO<>(200, "리뷰 답글 삭제가 완료되었습니다.", null));
		} else if (result == shelterService.NOT_REVIEW) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "작성된 리뷰글이 존재하지 않습니다."));
		} else if (result == shelterService.NOT_SHELTER_MANAGER) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "담당 보호소 관리자가 아니면 접근할 수 없습니다."));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "오류가 발생했습니다. 관리자에게 문의하세요."));
		}
	}

	/**
	 * 해당 보호시설 입양 리뷰글에 대한 답글 작성 메서드
	 * 
	 * @param dto     입양 리뷰 답변글
	 * @param session 로그인 검증 세션
	 * 
	 * @return 해당 보호시설 입양 리뷰글 답글
	 */
	@PostMapping("/adoptionReviews/reply")
	public ResponseEntity<?> addAdoptionReviewApply(@Valid @RequestBody ManageAdoptionReplyRequestDTO dto,
			HttpSession session) {

		LoginResponseDTO loginUser = shelterUserCheck(session);
		int userIdx = loginUser.getIdx();
		dto.setUserIdx(userIdx);

		int result = shelterService.addAdoptionReviewApply(dto, userIdx, dto.getRef());

		if (result == shelterService.NOT_REVIEW) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(404, "작성된 리뷰글이 존재하지 않습니다."));
		} else if (result == shelterService.UPDATE_OK) {
			return ResponseEntity.ok(new OkResponseDTO<>(201, "답글 등록이 완료되었습니다.", null));
		} else if (result == shelterService.NOT_ALLOWED_REPLY) {
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "답글은 한 번만 등록할 수 있습니다.")); 
		} else if (result == shelterService.NOT_SHELTER_MANAGER) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "담당 보호소 관리자가 아니면 접근할 수 없습니다."));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "오류가 발생했습니다. 관리자에게 문의하세요."));
		}
	}

	/**
	 * 해당 보호시설 입양 리뷰글에 대한 답글 수정 메서드
	 * 
	 * @param reviewIdx 입양 리뷰 글 번호
	 * @param dto       입양 리뷰
	 * @param session   로그인 검증 세션
	 * 
	 * @return 해당 보호시설 입양 리뷰글 답글 수정
	 */
	@PutMapping("/adoptionReviews/reply/update/{idx}")
	public ResponseEntity<?> updateAdoptionReviewApply(@PathVariable int idx,
			@Valid @RequestBody ManageAdoptionReplyRequestDTO dto, HttpSession session) {

		LoginResponseDTO loginUser = shelterUserCheck(session);

		int userIdx = loginUser.getIdx();
		dto.setUserIdx(userIdx);
		dto.setReviewIdx(idx);

		int result = shelterService.updateAdoptionReviewApply(dto, userIdx, idx);

		if (result == shelterService.UPDATE_OK) {
			return ResponseEntity.ok(new OkResponseDTO<>(200, "리뷰 답글 수정이 완료되었습니다.", null));
		} else if (result == shelterService.NOT_REVIEW) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "작성된 리뷰글이 존재하지 않습니다."));
		} else if (result == shelterService.NOT_SHELTER_MANAGER) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(new ErrorResponseDTO(403, "담당 보호소 관리자가 아니면 접근할 수 없습니다."));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "오류가 발생했습니다. 관리자에게 문의하세요."));
		}
	}

	/**
	 * 해당 보호시설 입양 리뷰글에 대한 답글 삭제 메서드
	 * 
	 * @param reviewIdx 입양 리뷰 글 번호
	 * @param session   로그인 검증 세션
	 * 
	 * @return 해당 보호시설 입양 리뷰글 답글 삭제
	 */
	@DeleteMapping("/adoptionReviews/reply/{idx}")
	public ResponseEntity<?> deleteAdoptionReviewApply(@PathVariable("idx") int idx, HttpSession session) {

		LoginResponseDTO loginUser = shelterUserCheck(session);

		int userIdx = loginUser.getIdx();

		int result = shelterService.deleteAdoptionReviewApply(userIdx, idx);

		if (result == shelterService.DELETE_OK) {
			return ResponseEntity.ok(new OkResponseDTO<>(200, "리뷰 답글 삭제가 완료되었습니다.", null));
		} else if (result == shelterService.NOT_REVIEW) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "작성된 리뷰글이 존재하지 않습니다."));
		} else if (result == shelterService.NOT_SHELTER_MANAGER) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(400, "담당 보호소 관리자가 아니면 접근할 수 없습니다."));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "오류가 발생했습니다. 관리자에게 문의하세요."));
		}
	}

	/**
	 * 해당 보호시설 게시판 글 목록 조회 메서드
	 * 
	 * @param cp      페이지번호
	 * @param session 로그인 검증 세션
	 * 
	 * @return 해당 보호시설 게시판 목록
	 */
	@GetMapping("/boards")
	public ResponseEntity<?> getShelterboardList(@RequestParam(value = "cp", defaultValue = "0") int cp,
			HttpSession session) {

		LoginResponseDTO loginUser = shelterUserCheck(session);

		int userIdx = loginUser.getIdx();

		List<ShelterBoardResponseDTO> boardLists = shelterService.getShelterBoardList(userIdx, cp);
		PageInformationDTO pageInfo = shelterService.getShelterBoardTotalCnt(userIdx, cp);

		if (boardLists == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "오류가 발생했습니다. 관리자에게 문의하세요."));
		} else if (boardLists.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "등록된 공지사항이 없습니다."));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkPageResponseDTO<List<ShelterBoardResponseDTO>>(200, "보호소 게시판 목록 조회 성공", boardLists, pageInfo));
		}
	}

	/**
	 * 해당 보호시설 게시판 상세정보 조회 메서드
	 * 
	 * @param idx     게시판 번호
	 * @param session 로그인 검증 세션
	 * 
	 * @return 해당 보호시설 게시판 상세 정보
	 */
	@GetMapping("/boards/{idx}")
	public ResponseEntity<?> getShelterBoardDetail(@PathVariable int idx, HttpSession session) {

		LoginResponseDTO loginUser = shelterUserCheck(session);

		int userIdx = loginUser.getIdx();

		ShelterBoardResponseDTO dto = shelterService.getShelterBoardDetail(idx, userIdx);

		if (dto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "삭제되거나 없는 게시물 입니다."));
		} else {
			return ResponseEntity.ok(new OkResponseDTO<ShelterBoardResponseDTO>(200, "게시물 상세정보 조회 성공", dto));
		}
	}

	/**
	 * 해당 보호시설 게시물 등록 메서드
	 * 
	 * @param dto     보호시설 게시판
	 * @param session 로그인 검증 세션
	 * 
	 * @return 해당 보호시설 게시물
	 */
	@PostMapping("/boards")
	public ResponseEntity<?> addShelterBoard(@Valid @RequestBody ShelterBoardRequestDTO dto, HttpSession session) {

		LoginResponseDTO loginUser = shelterUserCheck(session);
		int userIdx = loginUser.getIdx();

		int result = shelterService.addShelterBoard(dto, userIdx);

		if (result == shelterService.WRITE_OK) {
			Integer createdIdx = dto.getIdx();
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new OkResponseDTO<Integer>(201, "게시물 등록 완료", createdIdx));
		} else if (result == shelterService.NOT_SHELTER_MANAGER) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "담당 보호소 관리자가 아니면 접근할 수 없습니다."));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "오류가 발생했습니다. 관리자에게 문의하세요."));
		}
	}

	/**
	 * 해당 보호시설 게시물 등록 시 파일 업로드 메서드
	 * 
	 * @param files 업로드 한 파일
	 * @param idx   게시물 등록시 생성되는 번호
	 * 
	 * @return 파일 업로드 성공 여부
	 */
	@PostMapping("/boards/upload/{idx}")
	public ResponseEntity<?> uploadBoardFile(MultipartFile[] files, @PathVariable int idx) {

		int result = shelterService.uploadBoardFile(files, idx);
		if (result == shelterService.UPLOAD_OK) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new OkResponseDTO<Void>(201, "파일 업로드가 완료되었습니다.", null));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "파일 업로드 실패"));
		}
	}

	/**
	 * 해당 보호시설 게시물 수정 메서드
	 * 
	 * @param idx     게시물 번호
	 * @param dto     보호시설 게시판
	 * @param session 로그인 검증 세션
	 * 
	 * @return 보호시설 게시물 수정 여부
	 */
	@PutMapping("/boards/{idx}")
	public ResponseEntity<?> updateVolunteerReviewApply(@PathVariable int idx,
			@Valid @RequestBody ShelterBoardRequestDTO dto, HttpSession session) {

		LoginResponseDTO loginUser = shelterUserCheck(session);
		int userIdx = loginUser.getIdx();

		dto.setUserIdx(userIdx);
		dto.setIdx(idx);

		int result = shelterService.updateShelterBoard(dto);

		if (result == shelterService.UPDATE_OK) {
			return ResponseEntity.ok(new OkResponseDTO<>(200, "게시글 수정이 완료되었습니다.", null));
		} else if (result == shelterService.NOT_EXIST_BOARD) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "해당 게시글이 존재하지 않습니다."));
		} else if (result == shelterService.NOT_SHELTER_MANAGER) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "작성한 담당자만 수정이 가능합니다."));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "오류가 발생했습니다. 관리자에게 문의하세요."));
		}
	}

	/**
	 * 해당 보호시설 게시물 삭제 메서드
	 * 
	 * @param idx     게시물 번호
	 * @param session 로그인 검증 세션
	 * 
	 * @return 보호시설 게시물 삭제 여부
	 */
	@DeleteMapping("/boards/{idx}")
	public ResponseEntity<?> updateVolunteerReviewApply(@PathVariable int idx, HttpSession session) {

		LoginResponseDTO loginUser = shelterUserCheck(session);
		int userIdx = loginUser.getIdx();

		ShelterBoardRequestDTO dto = new ShelterBoardRequestDTO();
		dto.setUserIdx(userIdx);
		dto.setIdx(idx);

		int result = shelterService.deleteShelterBoard(dto, idx);

		if (result == shelterService.DELETE_OK) {
			return ResponseEntity.ok(new OkResponseDTO<>(200, "게시글 삭제가 완료되었습니다.", null));
		} else if (result == shelterService.NOT_EXIST_BOARD) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "해당 게시글이 존재하지 않습니다."));
		} else if (result == shelterService.NOT_SHELTER_MANAGER) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "작성한 담당자만 수정이 가능합니다."));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "오류가 발생했습니다. 관리자에게 문의하세요."));
		}
	}

	/**
	 * (공통) 로그인 및 보호시설 사용자 검증 메서드
	 * 
	 * @param session 로그인 검증 세션
	 * 
	 * @return 보호시설 계정으로 로그인한 관리자
	 */
	public LoginResponseDTO shelterUserCheck(HttpSession session) {
		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");

		if (loginUser == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인 후 이용가능합니다.");
		}
		if (loginUser.getUserTypeIdx() != 2) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "보호소 사용자만 접근 가능합니다.");
		}

		return loginUser;
	}

}
