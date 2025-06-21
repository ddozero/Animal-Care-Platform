package com.animal.api.shelter.controller;

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
import com.animal.api.shelter.model.response.AllShelterListResponseDTO;
import com.animal.api.shelter.model.response.ShelterAnimalsResponseDTO;
import com.animal.api.shelter.model.response.ShelterBoardListResponseDTO;
import com.animal.api.shelter.model.response.ShelterDetailResponseDTO;
import com.animal.api.shelter.model.response.ShelterVolunteersResponseDTO;
import com.animal.api.shelter.service.UserShelterService;

/**
 * 사용자 기준 보호시설 관련 컨트롤러 클래스
 * 
 * @author Rege-97
 * @since 2025-06-20
 * @see com.animal.api.shelter.model.response.AllShelterListResponseDTO
 * @see com.animal.api.shelter.model.response.ShelterAnimalsResponseDTO
 * @see com.animal.api.shelter.model.response.ShelterDetailResponseDTO
 * @see com.animal.api.shelter.model.response.ShelterVolunteersResponseDTO
 * @see com.animal.api.shelter.model.response.ShelterVolunteersResponseDTO
 */
@RestController
@RequestMapping("/api/shelters")
public class UserShelterController {

	@Autowired
	private UserShelterService service;

	/**
	 * 보호시설 조회 메서드
	 * 
	 * @param cp             현재 페이지
	 * @param shelterName    검색된 보호소 이름
	 * @param shelterAddress 선택된 보호소 주소
	 * @param shelterType    선택된 보호소 타입
	 * 
	 * @return 조회된 보호소의 리스트
	 */
	@GetMapping
	public ResponseEntity<?> getShelters(@RequestParam(value = "cp", defaultValue = "0") int cp,
			@RequestParam(value = "shelterName", required = false) String shelterName,
			@RequestParam(value = "shelterAddress", required = false) String shelterAddress,
			@RequestParam(value = "shelterType", required = false) String shelterType) {
		int listSize = 3;
		List<AllShelterListResponseDTO> shelterList = null;

		if (shelterName != null || shelterAddress != null || shelterType != null) {
			shelterList = service.searchShelters(listSize, cp, shelterName, shelterAddress, shelterType);
		} else {
			shelterList = service.getAllShelters(listSize, cp);
		}

		if (shelterList == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "잘못된 접근입니다."));
		} else if (shelterList.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "조회된 데이터가 없습니다."));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkResponseDTO<List<AllShelterListResponseDTO>>(200, "조회 성공", shelterList));
		}
	}

	/**
	 * 보호시설의 상세 정보를 확인하는 메서드
	 * 
	 * @param idx 보호시설의 idx
	 * @return 해당 보호시설의 상세정보
	 */
	@GetMapping("/{idx}")
	public ResponseEntity<?> getShelterDetail(@PathVariable int idx) {
		ShelterDetailResponseDTO dto = service.getShelterDetail(idx);
		if (dto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "해당 보호시설이 존재하지 않습니다."));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkResponseDTO<ShelterDetailResponseDTO>(200, "조회 성공", dto));
		}
	}

	/**
	 * 보호시설의 상세정보에서 해당 보호시설의 봉사 컨텐츠를 조회
	 * 
	 * @param idx 보호시설의 idx
	 * @param cp  봉사 컨텐츠의 현재 페이지
	 * @return 해당 보호시설의 봉사 컨텐츠
	 */
	@GetMapping("/{idx}/volunteers")
	public ResponseEntity<?> getShelterVolunteers(@PathVariable int idx,
			@RequestParam(value = "cp", defaultValue = "0") int cp) {
		int listSize = 3;
		List<ShelterVolunteersResponseDTO> volunteerList = service.getShelterVolunteers(listSize, cp, idx);

		if (volunteerList == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "잘못된 접근입니다."));
		} else if (volunteerList.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "조회된 데이터가 없습니다."));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkResponseDTO<List<ShelterVolunteersResponseDTO>>(200, "조회 성공", volunteerList));
		}
	}

	/**
	 * 보호시설의 상세정보에서 해당 보호시설의 유기동물들을 조회
	 * 
	 * @param idx            보호시설의 idx
	 * @param cp             봉사 컨텐츠의 현재 페이지
	 * @param type           동물 유형
	 * @param breed          동물 품종
	 * @param gender         동물 성별
	 * @param neuter         중성화 여부
	 * @param age            동물 나이
	 * @param adoptionStatus 입양 상태
	 * @param personality    동물 성격
	 * @param size           동물 크기
	 * @param name           동물 이름
	 * @return 해당 보호시설의 유기동물 리스트
	 */
	@GetMapping("/{idx}/animals")
	public ResponseEntity<?> getAllShelterAnimals(@PathVariable int idx,
			@RequestParam(value = "cp", defaultValue = "0") int cp,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "breed", required = false) String breed,
			@RequestParam(value = "gender", required = false) String gender,
			@RequestParam(value = "neuter", defaultValue = "0") int neuter,
			@RequestParam(value = "age", defaultValue = "0") int age,
			@RequestParam(value = "adoptionStatus", required = false) String adoptionStatus,
			@RequestParam(value = "personality", required = false) String personality,
			@RequestParam(value = "size", defaultValue = "0") int size,
			@RequestParam(value = "name", required = false) String name) {
		int listSize = 3;

		List<ShelterAnimalsResponseDTO> animalList = null;

		if (type != null || breed != null || gender != null || neuter != 0 || age != 0 || adoptionStatus != null
				|| personality != null || size != 0 || name != null) {
			animalList = service.searchShelterAnimals(idx, listSize, cp, type, breed, gender, neuter, age,
					adoptionStatus, personality, size, name);
		} else {
			animalList = service.getAllShelterAnimals(listSize, cp, idx);
		}

		if (animalList == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "잘못된 접근입니다."));
		} else if (animalList.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "조회된 데이터가 없습니다."));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkResponseDTO<List<ShelterAnimalsResponseDTO>>(200, "조회 성공", animalList));
		}
	}

	/**
	 * 보호시설의 상세 정보에서 보호시설이 남긴 게시글 조회
	 * 
	 * @param idx 보호시설의 idx
	 * @param cp  현재 페이지
	 * @return 해당 보호시설의 게시물 리스트
	 */
	@GetMapping("/{idx}/boards")
	public ResponseEntity<?> getShelterBoards(@PathVariable int idx,
			@RequestParam(value = "cp", defaultValue = "0") int cp) {
		int listSize = 3;

		List<ShelterBoardListResponseDTO> boardList = service.getShelterBoards(listSize, cp, idx);

		if (boardList == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "잘못된 접근입니다."));
		} else if (boardList.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "조회된 데이터가 없습니다."));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkResponseDTO<List<ShelterBoardListResponseDTO>>(200, "조회 성공", boardList));
		}
	}

}
