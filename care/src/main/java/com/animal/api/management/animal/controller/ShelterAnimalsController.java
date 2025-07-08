package com.animal.api.management.animal.controller;

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

import com.animal.api.animal.model.response.AnimalDetailResponseDTO;
import com.animal.api.animal.service.UserAnimalService;
import com.animal.api.auth.model.response.LoginResponseDTO;
import com.animal.api.common.model.ErrorResponseDTO;
import com.animal.api.common.model.OkPageResponseDTO;
import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.common.model.PageInformationDTO;
import com.animal.api.management.animal.model.request.AdoptionConsultStatusRequestDTO;
import com.animal.api.management.animal.model.request.AnimalInsertRequestDTO;
import com.animal.api.management.animal.model.request.AnimalUpdateRequestDTO;
import com.animal.api.management.animal.model.response.AdoptionConsultDetailResponseDTO;
import com.animal.api.management.animal.model.response.AdoptionConsultListResponseDTO;
import com.animal.api.management.animal.model.response.AnimalAddShelterInfoResponseDTO;
import com.animal.api.management.animal.service.ShelterAnimalsService;
import com.animal.api.shelter.model.response.ShelterAnimalsResponseDTO;
import com.animal.api.shelter.service.UserShelterService;

/**
 * 보호시설의 관리 페이지에서 유기동물에 관련되어 있는 컨트롤러 클래스
 * 
 * @author Rege-97
 * @since 2026-06-23
 * @see com.animal.api.management.animal.model.response.AnimalAddShelterInfoResponseDTO
 * @see com.animal.api.management.animal.model.request.AnimalInsertRequestDTO
 * @see com.animal.api.management.animal.model.request.AnimalUpdateRequestDTO
 * @see com.animal.api.management.animal.model.response.AdoptionConsultListResponseDTO
 * @see com.animal.api.management.animal.model.response.AdoptionConsultDetailResponseDTO
 */

@RestController
@RequestMapping("api/management/animals")
public class ShelterAnimalsController {

	@Autowired
	private ShelterAnimalsService shelterAnimalsService;
	@Autowired
	private UserShelterService userShelterService;
	@Autowired
	private UserAnimalService userAnimalService;

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
	 * @param session        로그인 정보 검증을 위한 세션
	 * @return 해당 보호시설의 유기동물 리스트
	 */
	@GetMapping
	public ResponseEntity<?> getAllShelterAnimals(@RequestParam(value = "cp", defaultValue = "0") int cp,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "breed", required = false) String breed,
			@RequestParam(value = "gender", required = false) String gender,
			@RequestParam(value = "neuter", defaultValue = "0") int neuter,
			@RequestParam(value = "age", defaultValue = "0") int age,
			@RequestParam(value = "adoptionStatus", required = false) String adoptionStatus,
			@RequestParam(value = "personality", required = false) String personality,
			@RequestParam(value = "size", defaultValue = "0") int size,
			@RequestParam(value = "name", required = false) String name, HttpSession session) {
		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");

		if (loginUser == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginUser.getUserTypeIdx() != 2) { // 보호시설 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "보호시설 회원만 접근 가능합니다."));
		}

		List<ShelterAnimalsResponseDTO> animalList = null;
		PageInformationDTO pageInfo = null;

		if (type != null || breed != null || gender != null || neuter != 0 || age != 0 || adoptionStatus != null
				|| personality != null || size != 0 || name != null) {
			animalList = userShelterService.searchShelterAnimals(loginUser.getIdx(), cp, type, breed, gender, neuter,
					age, adoptionStatus, personality, size, name);
			pageInfo = userShelterService.searchShelterAnimalsPageInfo(loginUser.getIdx(), cp, type, breed, gender,
					neuter, age, adoptionStatus, personality, size, name);
		} else {
			animalList = userShelterService.getAllShelterAnimals(cp, loginUser.getIdx());
			pageInfo = userShelterService.getAllShelterAnimalsPageInfo(cp, loginUser.getIdx());
		}

		if (animalList == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "잘못된 접근입니다."));
		} else if (animalList.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "조회된 데이터가 없습니다."));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkPageResponseDTO<List<ShelterAnimalsResponseDTO>>(200, "조회 성공", animalList, pageInfo));
		}
	}

	/**
	 * 유기동물의 상세 정보를 조회하는 메서드
	 * 
	 * @param idx     유기동물 관리번호
	 * @param session 로그인 정보 검증을 위한 세션
	 * @return 사용자에게 보여줄 유기동물과 보호소 정보
	 */
	@GetMapping("/{idx}")
	public ResponseEntity<?> getAnimalDetail(@PathVariable int idx, HttpSession session) {
		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");

		if (loginUser == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginUser.getUserTypeIdx() != 2) { // 보호시설 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "보호시설 회원만 접근 가능합니다."));
		}

		AnimalDetailResponseDTO dto = userAnimalService.getAnimalDetail(idx);

		if (loginUser.getIdx() != dto.getUserIdx()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "해당 보호시설의 유기동물이 아닙니다."));
		}

		if (dto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "해당 동물이 존재하지 않습니다."));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkResponseDTO<AnimalDetailResponseDTO>(200, "조회 성공", dto));
		}
	}

	/**
	 * 유기동물 등록 시 내 보호시설 확인 메서드
	 * 
	 * @param session 로그인 정보 검증을 위한 세션
	 * @return 로그인된 보호소 정보 반환
	 */
	@GetMapping("/shelter")
	public ResponseEntity<?> getShelterProfile(HttpSession session) {
		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");

		if (loginUser == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginUser.getUserTypeIdx() != 2) { // 보호시설 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "보호시설 회원만 접근 가능합니다."));
		}

		AnimalAddShelterInfoResponseDTO dto = shelterAnimalsService.getShelterProfile(loginUser.getIdx());

		if (dto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "해당 보호시설을 찾을 수 없습니다."));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkResponseDTO<AnimalAddShelterInfoResponseDTO>(200, "조회 성공", dto));
		}
	}

	/**
	 * 유기동물 등록 관련 메서드
	 * 
	 * @param dto     유기동물 정보 폼 데이터
	 * @param session 로그인 검증을 위한 세션
	 * @return 생성된 idx
	 */
	@PostMapping
	public ResponseEntity<?> insertAnimal(@Valid @RequestBody AnimalInsertRequestDTO dto, HttpSession session) {
		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");

		if (loginUser == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginUser.getUserTypeIdx() != 2) { // 보호시설 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "보호시설 회원만 접근 가능합니다."));
		}

		int result = shelterAnimalsService.insertAnimal(dto, loginUser.getIdx());

		if (result == shelterAnimalsService.POST_SUCCESS) {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("createdIdx", dto.getIdx());
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new OkResponseDTO<Map<String, Integer>>(201, "유기동물 등록 성공", map));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "유기동물 등록 실패"));
		}
	}

	/**
	 * 유기동물 이미지 업로드 메서드
	 * 
	 * @param files 프론트 영역에서 받은 유기동물 이미지
	 * @param idx   유기동물이 생성되면서 생긴 관리번호
	 * @return 파일 업로드 성공 또는 실패
	 */
	@PostMapping("/upload/{idx}/{method}")
	public ResponseEntity<?> uploadAnimalImage(MultipartFile[] files, @PathVariable int idx,
			@PathVariable String method) {
		int result = shelterAnimalsService.uploadAnimalImage(files, idx, method);
		if (result == shelterAnimalsService.UPLOAD_SUCCESS) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new OkResponseDTO<Void>(201, "이미지 업로드 성공", null));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "이미지 업로드 실패"));
		}
	}

	/**
	 * 유기동물의 정보를 수정하는 메서드
	 * 
	 * @param animalIdx 유기동물 관리번호
	 * @param dto       수정될 내용을 담은 폼 데이터
	 * @param session   로그인 검증을 위한 세션
	 * @return 성공 또는 실패 메세지
	 */
	@PutMapping("/{animalIdx}")
	public ResponseEntity<?> updateAnimal(@PathVariable int animalIdx, @Valid @RequestBody AnimalUpdateRequestDTO dto,
			HttpSession session) {
		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");

		if (loginUser == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginUser.getUserTypeIdx() != 2) { // 보호시설 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "보호시설 회원만 접근 가능합니다."));
		}

		int result = shelterAnimalsService.updateAnimal(dto, animalIdx, loginUser.getIdx());

		if (result == shelterAnimalsService.UPDATE_SUCCESS) {
			return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<Void>(200, "유기동물 수정 성공", null));
		} else if (result == shelterAnimalsService.NOT_ANIMAL) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponseDTO(404, "해당 유기동물이 존재하지 않거나 삭제되었습니다."));
		} else if (result == shelterAnimalsService.NOT_OWNED_ANIMAL) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "해당 보호시설의 유기동물이 아닙니다."));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "유기동물 수정 실패"));
		}
	}

	/**
	 * 유기동물의 데이터를 삭제하는 메서드
	 * 
	 * @param animalIdx 유기동물 관리번호
	 * @param session   로그인 검증을 위한 세션
	 * @return 성공 또는 실패 메세지
	 */
	@DeleteMapping("/{animalIdx}")
	public ResponseEntity<?> deleteAnimal(@PathVariable int animalIdx, HttpSession session) {
		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");

		if (loginUser == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginUser.getUserTypeIdx() != 2) { // 보호시설 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "보호시설 회원만 접근 가능합니다."));
		}

		int result = shelterAnimalsService.deleteAnimal(animalIdx, loginUser.getIdx());

		if (result == shelterAnimalsService.DELETE_SUCCESS) {
			return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<Void>(200, "유기동물 삭제 성공", null));
		} else if (result == shelterAnimalsService.NOT_ANIMAL) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponseDTO(404, "해당 유기동물이 존재하지 않거나 이미 삭제되었습니다."));
		} else if (result == shelterAnimalsService.NOT_OWNED_ANIMAL) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "해당 보호시설의 유기동물이 아닙니다."));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "유기동물 삭제 실패"));
		}
	}

	/**
	 * 해당 보호시설의 입양 신청 내역 리스트를 조회하는 메서드
	 * 
	 * @param cp      현재 페이지
	 * @param session 로그인 검증을 위한 세션
	 * @return 입양 상담 신청내역 리스트
	 */
	@GetMapping("/adoptions")
	public ResponseEntity<?> getAdoptionConsultList(@RequestParam(value = "cp", defaultValue = "0") int cp,
			HttpSession session) {
		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");

		if (loginUser == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginUser.getUserTypeIdx() != 2) { // 보호시설 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "보호시설 회원만 접근 가능합니다."));
		}

		List<AdoptionConsultListResponseDTO> consultList = shelterAnimalsService
				.getAdoptionConsultList(loginUser.getIdx(), cp);
		PageInformationDTO pageInfo = shelterAnimalsService.getAdoptionConsultListPageInfo(loginUser.getIdx(), cp);

		if (consultList == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "잘못된 요청 입니다."));
		} else if (consultList.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "조회된 데이터가 없습니다."));
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(
					new OkPageResponseDTO<List<AdoptionConsultListResponseDTO>>(200, "조회 성공", consultList, pageInfo));
		}
	}

	/**
	 * 해당 보호시설의 입양 상담 신청의 상세정보 조회
	 * 
	 * @param idx     입양 상담 신청 번호
	 * @param session 로그인 검증을 위한 세션
	 * @return 입양 상담 신청 상세정보
	 */
	@GetMapping("/adoptions/{idx}")
	public ResponseEntity<?> getAdoptionConsultDetail(@PathVariable int idx, HttpSession session) {
		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");

		if (loginUser == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginUser.getUserTypeIdx() != 2) { // 보호시설 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "보호시설 회원만 접근 가능합니다."));
		}

		AdoptionConsultDetailResponseDTO dto = shelterAnimalsService.getAdoptionConsultDetail(idx);

		if (dto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "해당 상담 신청을 찾을 수 없습니다."));
		} else if (dto.getShelterIdx() != loginUser.getIdx()) { // 해당 입양 상담 신청이 로그인한 보호시설의 신청이 아닐 때
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(403, "해당 보호시설의 상담 신청이 아닙니다."));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkResponseDTO<AdoptionConsultDetailResponseDTO>(200, "조회 성공", dto));
		}
	}

	/**
	 * 해당 보호시설의 입양 상담 신청의 상태를 변경하는 메서드
	 * 
	 * @param consultIdx 입양 상담 신청 번호
	 * @param dto        변경할 상태
	 * @param session    로그인 검증을 위한 세션
	 * @return 성공 또는 실패 메세지
	 */
	@PutMapping("/adoptions/{consultIdx}")
	public ResponseEntity<?> updateAdoptionConsultStatus(@PathVariable int consultIdx,
			@Valid @RequestBody AdoptionConsultStatusRequestDTO dto, HttpSession session) {
		LoginResponseDTO loginUser = (LoginResponseDTO) session.getAttribute("loginUser");

		if (loginUser == null) { // 로그인 여부 검증
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(401, "로그인 후 이용해주세요."));
		}

		if (loginUser.getUserTypeIdx() != 2) { // 보호시설 회원 검증
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403, "보호시설 회원만 접근 가능합니다."));
		}

		int result = shelterAnimalsService.updateAdoptionConsultStatus(dto, loginUser.getIdx(), consultIdx);

		if (result == shelterAnimalsService.UPDATE_SUCCESS) {
			return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<Void>(200, "상태 변경 성공", null));
		} else if (result == shelterAnimalsService.NOT_CONSULT) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "해당 상담 신청을 찾을 수 없습니다."));
		} else if (result == shelterAnimalsService.NOT_OWNED_CONSULT) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(new ErrorResponseDTO(403, "로그인한 보호시설의 상담신청이 아닙니다."));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "상태 변경 실패"));
		}
	}

}
