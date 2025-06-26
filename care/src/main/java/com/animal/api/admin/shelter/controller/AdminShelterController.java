package com.animal.api.admin.shelter.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.animal.model.response.AnimalDetailResponseDTO;
import com.animal.api.animal.service.UserAnimalService;
import com.animal.api.auth.model.response.LoginResponseDTO;
import com.animal.api.common.model.ErrorResponseDTO;
import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.management.animal.service.ShelterAnimalsService;
import com.animal.api.shelter.model.response.AllShelterListResponseDTO;
import com.animal.api.shelter.model.response.ShelterAnimalsResponseDTO;
import com.animal.api.shelter.model.response.ShelterDetailResponseDTO;
import com.animal.api.shelter.model.response.ShelterVolunteersResponseDTO;
import com.animal.api.shelter.service.UserShelterService;
import com.animal.api.volunteers.model.response.AllVolunteersResponseDTO;
import com.animal.api.volunteers.service.UserVolunteersService;

/**
 * 사이트 관리자 페이지 내의 보호시설 관리 페이지 컨트롤러
 * 
 * @author Rege-97
 * @since 2025-06-25
 * @see com.animal.api.shelter.model.response.AllShelterListResponseDTO
 */
@RestController
@RequestMapping("/api/admin/shelters")
public class AdminShelterController {

	@Autowired
	private UserShelterService userShelterService;
	@Autowired
	private UserAnimalService userAnimalService;
	@Autowired
	private ShelterAnimalsService shelterAnimalService;
	@Autowired
	private UserVolunteersService userVolunteerService;

	/**
	 * 사이트 관리자 페이지의 보호시설 조회 및 검색 메서드
	 * 
	 * @param cp             현재 페이지
	 * @param shelterName    검색된 보호소 이름
	 * @param shelterAddress 선택된 보호소 주소
	 * @param shelterType    선택된 보호소 타입
	 * @param session        로그인 검증을 위한 세션
	 * 
	 * @return 조회된 보호소의 리스트
	 */
	@GetMapping
	public ResponseEntity<?> getShelters(@RequestParam(value = "cp", defaultValue = "0") int cp,
			@RequestParam(value = "shelterName", required = false) String shelterName,
			@RequestParam(value = "shelterAddress", required = false) String shelterAddress,
			@RequestParam(value = "shelterType", required = false) String shelterType, HttpSession session) {
		LoginResponseDTO loginAdmin = (LoginResponseDTO) session.getAttribute("loginAdmin");

		if (loginAdmin == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginAdmin.getUserTypeIdx() != 3) { // 관리자 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "관리자만 접근 가능합니다."));
		}

		int listSize = 3;
		List<AllShelterListResponseDTO> shelterList = null;

		if (shelterName != null || shelterAddress != null || shelterType != null) {
			shelterList = userShelterService.searchShelters(listSize, cp, shelterName, shelterAddress, shelterType);
		} else {
			shelterList = userShelterService.getAllShelters(listSize, cp);
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
	 * 사이트 관리 페이지에서 해당 보호시설의 상세 정보를 확인하는 메서드
	 * 
	 * @param idx     보호시설의 idx
	 * @param session 로그인 검증을 위한 세션
	 * @return 해당 보호시설의 상세정보
	 */
	@GetMapping("/{idx}")
	public ResponseEntity<?> getShelterDetail(@PathVariable int idx, HttpSession session) {
		LoginResponseDTO loginAdmin = (LoginResponseDTO) session.getAttribute("loginAdmin");

		if (loginAdmin == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginAdmin.getUserTypeIdx() != 3) { // 관리자 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "관리자만 접근 가능합니다."));
		}

		ShelterDetailResponseDTO dto = userShelterService.getShelterDetail(idx);
		if (dto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "해당 보호시설이 존재하지 않습니다."));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkResponseDTO<ShelterDetailResponseDTO>(200, "조회 성공", dto));
		}
	}

	/**
	 * 사이트 관리자 페이지에서 해당 보호시설의 유기동물들을 조회
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
	 * @param session        로그인 검증을 위한 세션
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
			@RequestParam(value = "name", required = false) String name, HttpSession session) {
		LoginResponseDTO loginAdmin = (LoginResponseDTO) session.getAttribute("loginAdmin");

		if (loginAdmin == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginAdmin.getUserTypeIdx() != 3) { // 관리자 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "관리자만 접근 가능합니다."));
		}

		int listSize = 3;

		List<ShelterAnimalsResponseDTO> animalList = null;

		if (type != null || breed != null || gender != null || neuter != 0 || age != 0 || adoptionStatus != null
				|| personality != null || size != 0 || name != null) {
			animalList = userShelterService.searchShelterAnimals(idx, listSize, cp, type, breed, gender, neuter, age,
					adoptionStatus, personality, size, name);
		} else {
			animalList = userShelterService.getAllShelterAnimals(listSize, cp, idx);
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
	 * 사이트 관리자 페이지에서 유기동물의 상세 정보를 조회하는 메서드
	 * 
	 * @param shelterIdx 보호시설의 idx
	 * @param animalIdx  유기동물 관리번호
	 * @param session    로그인 검증을 위한 세션
	 * @return 유기동물 정보
	 */
	@GetMapping("/{shelterIdx}/animals/{animalIdx}")
	public ResponseEntity<?> getAnimalDetail(@PathVariable int shelterIdx, @PathVariable int animalIdx,
			HttpSession session) {
		LoginResponseDTO loginAdmin = (LoginResponseDTO) session.getAttribute("loginAdmin");

		if (loginAdmin == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginAdmin.getUserTypeIdx() != 3) { // 관리자 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "관리자만 접근 가능합니다."));
		}

		AnimalDetailResponseDTO dto = userAnimalService.getAnimalDetail(animalIdx);

		if (dto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "해당 동물이 존재하지 않습니다."));
		} else if (dto.getUserIdx() != shelterIdx) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "해당 보호시설의 동물이 아닙니다."));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkResponseDTO<AnimalDetailResponseDTO>(200, "조회 성공", dto));
		}
	}

	/**
	 * 사이트 관리자 페이지에서 유기동물의 데이터를 삭제하는 메서드
	 * 
	 * @param shelterIdx 보호시설의 idx
	 * @param animalIdx  유기동물 관리번호
	 * @param session    로그인 검증을 위한 세션
	 * @return 성공 또는 실패 메세지
	 */
	@DeleteMapping("/{shelterIdx}/animals/{animalIdx}")
	public ResponseEntity<?> deleteAnimal(@PathVariable int shelterIdx, @PathVariable int animalIdx,
			HttpSession session) {
		LoginResponseDTO loginAdmin = (LoginResponseDTO) session.getAttribute("loginAdmin");

		if (loginAdmin == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginAdmin.getUserTypeIdx() != 3) { // 관리자 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "관리자만 접근 가능합니다."));
		}

		int result = shelterAnimalService.deleteAnimal(animalIdx, shelterIdx);

		if (result == shelterAnimalService.DELETE_SUCCESS) {
			return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<Void>(200, "유기동물 삭제 성공", null));
		} else if (result == shelterAnimalService.NOT_ANIMAL) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponseDTO(404, "해당 유기동물이 존재하지 않거나 이미 삭제되었습니다."));
		} else if (result == shelterAnimalService.NOT_OWNED_ANIMAL) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorResponseDTO(400, "해당 보호시설의 유기동물이 아닙니다."));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "유기동물 삭제 실패"));
		}
	}

	/**
	 * 사이트 관리자 페이지에서 해당 보호시설의 봉사들을 조회
	 * 
	 * @param idx     보호시설의 idx
	 * @param cp      봉사 컨텐츠의 현재 페이지
	 * @param session 로그인 검증을 위한 세션
	 * @return 해당 보호시설의 봉사 컨텐츠
	 */
	@GetMapping("/{idx}/volunteers")
	public ResponseEntity<?> getShelterVolunteers(@PathVariable int idx,
			@RequestParam(value = "cp", defaultValue = "0") int cp, HttpSession session) {
		LoginResponseDTO loginAdmin = (LoginResponseDTO) session.getAttribute("loginAdmin");

		if (loginAdmin == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginAdmin.getUserTypeIdx() != 3) { // 관리자 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "관리자만 접근 가능합니다."));
		}
		int listSize = 3;
		List<ShelterVolunteersResponseDTO> volunteerList = userShelterService.getShelterVolunteers(listSize, cp, idx);

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
	 * 사이트 관리자 페이지에서 봉사의 상세 정보를 조회하는 메서드
	 * 
	 * @param shelterIdx   보호시설의 idx
	 * @param volunteeIidx 봉사 번호
	 * @param session      로그인 검증을 위한 세션
	 * @return 봉사 상세 정보
	 */

	@GetMapping("/{shelterIdx}/volunteers/{volunteerIdx}")
	public ResponseEntity<?> getVolunteersDetail(@PathVariable int shelterIdx, @PathVariable int volunteerIdx,
			HttpSession session) {
		LoginResponseDTO loginAdmin = (LoginResponseDTO) session.getAttribute("loginAdmin");

		if (loginAdmin == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginAdmin.getUserTypeIdx() != 3) { // 관리자 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "관리자만 접근 가능합니다."));
		}
		AllVolunteersResponseDTO dto = userVolunteerService.getVolunteersDetail(volunteerIdx);

		if (dto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "해당 봉사가 존재하지 않습니다."));
		} else if (dto.getUserIdx() != shelterIdx) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "해당 보호시설의 봉사가 아닙니다."));
		} else {
			return ResponseEntity.ok(new OkResponseDTO<AllVolunteersResponseDTO>(200, "봉사 상세정보 조회 성공", dto));
		}
	}

}
