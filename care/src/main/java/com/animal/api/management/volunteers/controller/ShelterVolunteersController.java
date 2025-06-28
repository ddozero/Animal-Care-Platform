package com.animal.api.management.volunteers.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.animal.api.auth.model.response.LoginResponseDTO;
import com.animal.api.common.model.ErrorResponseDTO;
import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.management.volunteers.model.request.ShelterVolunteersInsertDTO;
import com.animal.api.management.volunteers.model.response.ShelterVolunteersListResponseDTO;
import com.animal.api.management.volunteers.service.ShelterVolunteersService;

/**
 * 보호시설 기준 봉사 관련 컨트롤러 클래스
 * 
 * @author consgary
 * @since 2025.06.28
 * @see com.animal.api.management.volunteers.model.response.ShelterVolunteersListResponseDTO
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
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "데이터가 존재하지않음"));
		}
	}

	@PostMapping("/upload/{idx}")
	public ResponseEntity<?> uploadShelterVolunteerImage(MultipartFile[] files, @PathVariable int idx) {
		int result = service.uploadShelterVolunteerImage(files, idx);
		if (result == service.UPLOAD_SUCCESS) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new OkResponseDTO<Void>(201, "이미지 업로드 성공", null));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "이미지 업로드 실패"));
		}
	}

}
