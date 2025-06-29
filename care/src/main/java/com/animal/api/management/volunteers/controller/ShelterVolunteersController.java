package com.animal.api.management.volunteers.controller;

import java.util.HashMap;
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
import org.springframework.web.multipart.MultipartFile;

import com.animal.api.auth.model.response.LoginResponseDTO;
import com.animal.api.common.model.ErrorResponseDTO;
import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.management.volunteers.model.request.ShelterVolunteerUpdateRequestDTO;
import com.animal.api.management.volunteers.model.request.ShelterVolunteersInsertDTO;
import com.animal.api.management.volunteers.model.response.ShelterVolunteerApplicationsResponseDTO;
import com.animal.api.management.volunteers.model.response.ShelterVolunteerDetailResponseDTO;
import com.animal.api.management.volunteers.model.response.ShelterVolunteersListResponseDTO;
import com.animal.api.management.volunteers.service.ShelterVolunteersService;

/**
 * 보호시설 기준 봉사 관련 컨트롤러 클래스
 * 
 * @author consgary
 * @since 2025.06.29
 * @see com.animal.api.management.volunteers.model.response.ShelterVolunteersListResponseDTO
 * @see com.animal.api.management.volunteers.model.request.ShelterVolunteersInsertDTO
 * @see com.animal.api.management.volunteers.model.response.ShelterVolunteerDetailResponseDTO
 * @see com.animal.api.management.volunteers.model.request.ShelterVolunteerUpdateRequestDTO
 * @see com.animal.api.management.volunteers.model.response.ShelterVolunteerApplicationsResponseDTO
 */

@RestController
@RequestMapping("/api/management/volunteers")
public class ShelterVolunteersController {

	@Autowired
	private ShelterVolunteersService service;

	/**
	 * 보호시설이 등록한 봉사 전체 조회
	 * 
	 * @param cp      현재페이지
	 * @param session 로그인 검증용 세션
	 * @return 성공시 보호시설이 등록한 봉사 리스트와 메세지/실패시 메세지
	 */
	@GetMapping
	public ResponseEntity<?> getShelterVolunteers(@RequestParam(value = "cp", defaultValue = "0") int cp,
			HttpSession session) {
		int listSize = 3;
		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");

		if (loginUser == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginUser.getUserTypeIdx() != 2) { // 보호시설 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "보호시설 회원만 접근 가능합니다."));
		}

		List<ShelterVolunteersListResponseDTO> shelterVolunteersList = service
				.getShelterAllVolunteers(loginUser.getIdx(), listSize, cp);
		if (shelterVolunteersList == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "잘못된 요청"));
		} else if (shelterVolunteersList.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "데이터가 존재하지않음"));
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<List<ShelterVolunteersListResponseDTO>>(
					200, "보호시설에 등록된 봉사 조회 성공", shelterVolunteersList));
		}
	}

	/**
	 * 봉사 등록
	 * 
	 * @param dto     봉사 등록폼
	 * @param session 로그인 검증용 세션
	 * @return 성공시 메세지와 함께 게시글 idx(이미지업로드 활용)/실패시 실패 메세지
	 */
	@PostMapping
	public ResponseEntity<?> addShelterVolunteer(@Valid @RequestBody ShelterVolunteersInsertDTO dto,
			HttpSession session) {
		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");

		if (loginUser == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginUser.getUserTypeIdx() != 2) { // 보호시설 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "보호시설 회원만 접근 가능합니다."));
		}

		int result = service.addShelterVolunteer(dto);

		if (result == service.POST_SUCCESS) {
			Integer volunteerIdx = dto.getIdx();
			Map<String, Integer> map = new HashMap();
			map.put("createdIdx", volunteerIdx);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new OkResponseDTO<Map<String, Integer>>(201, "봉사 등록 성공", map));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "봉사 등록 실패"));
		}
	}

	/**
	 * 봉사 대표 이미지 업로드
	 * 
	 * @param files 프론트에서 받은 이미지
	 * @param idx   봉사 등록 되면서 생긴 번호
	 * @return 업로드 성공 또는 실패 메세지
	 */
	@PostMapping("/upload/{idx}")
	public ResponseEntity<?> uploadShelterVolunteerImage(MultipartFile[] files, @PathVariable int idx) {
		int result = service.uploadShelterVolunteerImage(files, idx);
		if (result == service.UPLOAD_SUCCESS) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new OkResponseDTO<Void>(201, "이미지 업로드 성공", null));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "이미지 업로드 실패"));
		}
	}

	/**
	 * 보호시설이 등록한 봉사 상세 조회
	 * 
	 * @param idx     봉사 번호
	 * @param session 로그인,보호소 검증용 세션
	 * @return 봉사 상세 정보
	 */
	@GetMapping("{idx}")
	public ResponseEntity<?> getShelterVolunteerDetail(@PathVariable int idx, HttpSession session) {
		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");
		if (loginUser == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginUser.getUserTypeIdx() != 2) { // 보호시설 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "보호시설 회원만 접근 가능합니다."));
		}
		ShelterVolunteerDetailResponseDTO volunteerDetail = service.getShelterVolunteerDetail(idx);

		if (volunteerDetail == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "봉사 상세 데이터가 존재하지않음"));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkResponseDTO<ShelterVolunteerDetailResponseDTO>(200, "봉사 상세 조회 성공", volunteerDetail));
		}
	}

	/**
	 * 봉사 수정
	 * 
	 * @param idx     봉사 번호
	 * @param dto     봉사 수정폼
	 * @param session 로그인,보호소 검증용 세션
	 * @return 성공시 메세지와 함께 게시글 idx(이미지업로드 활용)/실패시 실패 메세지
	 */
	@PutMapping("{idx}")
	public ResponseEntity<?> updateShelterVolunteer(@PathVariable int idx,
			@Valid @RequestBody ShelterVolunteerUpdateRequestDTO dto, HttpSession session) {
		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");
		if (loginUser == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginUser.getUserTypeIdx() != 2) { // 보호시설 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "보호시설 회원만 접근 가능합니다."));
		}

		int result = service.updateShelterVolunteer(dto, idx);

		if (result == service.UPDATE_SUCCESS) {
			Integer volunteerIdx = dto.getVolunteerIdx();
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("createIdx", volunteerIdx);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new OkResponseDTO<Map<String, Integer>>(201, "봉사  수정 성공", map));
		} else if (result == service.NOT_OWNED_VOLUNTEER) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "로그인한 봉사시설의 봉사가 아닙니다."));
		} else if (result == service.VOLUNTEER_NOT_FOUND) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "봉사가 존재 하지 않습니다"));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "봉사 수정 실패"));
		}
	}

	/**
	 * 봉사 삭제
	 * 
	 * @param idx     봉사 번호
	 * @param session 로그인,보호소 검증용 세션
	 * @return 성공,실패 메세지
	 */
	@DeleteMapping("{idx}")
	public ResponseEntity<?> deleteShelterVolunteer(@PathVariable int idx, HttpSession session) {
		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");
		if (loginUser == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginUser.getUserTypeIdx() != 2) { // 보호시설 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "보호시설 회원만 접근 가능합니다."));
		}

		int result = service.deleteShelterVolunteer(idx, loginUser.getIdx());

		if (result == service.DELETE_SUCCESS) {
			return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<Void>(200, "봉사 삭제 성공", null));
		} else if (result == service.VOLUNTEER_NOT_FOUND) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "봉사가 존재 하지 않습니다"));
		} else if (result == service.NOT_OWNED_VOLUNTEER) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "로그인한 봉사시설의 봉사가 아닙니다."));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "봉사 삭제 실패"));
		}
	}

	/**
	 * 봉사 신청한 신청서 전체 조회
	 * 
	 * @param cp      현재 페이지
	 * @param idx     봉사 번호
	 * @param session 로그인,보호소 검증용 세션
	 * @return 성공시 봉사 신청한 신청서 리스트와 메세지/실패시 메세지
	 */
	@GetMapping("{idx}/applications")
	public ResponseEntity<?> getShelterVolunteerApplications(@RequestParam(value = "cp", defaultValue = "0") int cp,
			@PathVariable int idx, HttpSession session) {
		int listSize = 3;
		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");

		if (loginUser == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginUser.getUserTypeIdx() != 2) { // 보호시설 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "보호시설 회원만 접근 가능합니다."));
		}

		List<ShelterVolunteerApplicationsResponseDTO> shelterVolunteerApplications = service
				.getShelterVolunteerApplications(idx, listSize, cp);
		if (shelterVolunteerApplications == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "잘못된 요청"));
		} else if (shelterVolunteerApplications.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "데이터가 존재하지않음"));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkResponseDTO<List<ShelterVolunteerApplicationsResponseDTO>>(200, "봉사에 신청한 신청서 조회 성공",
							shelterVolunteerApplications));
		}
	}
}
