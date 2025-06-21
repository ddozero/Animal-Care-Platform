package com.animal.api.management.shelter.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.auth.model.response.LoginResponseDTO;
import com.animal.api.common.model.ErrorResponseDTO;
import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.management.shelter.model.response.AllManageShelterDTO;
import com.animal.api.management.shelter.service.ShelterManageService;

@RestController
@RequestMapping("api/management/shelter")
public class ShelterManageController {

	@Autowired
	private ShelterManageService shelterService;

	@GetMapping
	public ResponseEntity<?> getShelterInfo(HttpSession session) {

		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");

		if (loginUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용가능"));
		}
		int userIdx = loginUser.getIdx();

		AllManageShelterDTO dto = shelterService.getShelterInfo(userIdx);

		if (dto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "회원이 존재하지 않음"));
		} else {
			return ResponseEntity.ok(new OkResponseDTO<AllManageShelterDTO>(200, "보호소 기본정보 조회 성공", dto));
		}
	}
}
