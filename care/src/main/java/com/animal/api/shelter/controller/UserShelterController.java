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

import com.animal.api.animal.model.response.AllAnimalListResponseDTO;
import com.animal.api.animal.model.response.AnimalDetailResponseDTO;
import com.animal.api.animal.service.UserAnimalService;
import com.animal.api.common.model.ErrorResponseDTO;
import com.animal.api.common.model.OkPageResponseDTO;
import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.common.model.PageInformationDTO;
import com.animal.api.shelter.model.response.AllShelterListResponseDTO;
import com.animal.api.shelter.model.response.ShelterAdoptionReviewResponseDTO;
import com.animal.api.shelter.model.response.ShelterAnimalsResponseDTO;
import com.animal.api.shelter.model.response.ShelterBoardDetailResponseDTO;
import com.animal.api.shelter.model.response.ShelterBoardListResponseDTO;
import com.animal.api.shelter.model.response.ShelterDetailResponseDTO;
import com.animal.api.shelter.model.response.ShelterVolunteerReviewResponseDTO;
import com.animal.api.shelter.model.response.ShelterVolunteersResponseDTO;
import com.animal.api.shelter.service.UserShelterService;
import com.animal.api.volunteers.model.response.AllVolunteersResponseDTO;
import com.animal.api.volunteers.service.UserVolunteersService;

/**
 * 사용자 기준 보호시설 관련 컨트롤러 클래스
 * 
 * @author Rege-97
 * @since 2025-06-26
 * @see com.animal.api.shelter.model.response.AllShelterListResponseDTO
 * @see com.animal.api.shelter.model.response.ShelterAdoptionReviewResponseDTO
 * @see com.animal.api.shelter.model.response.ShelterAnimalsResponseDTO
 * @see com.animal.api.shelter.model.response.ShelterBoardDetailResponseDTO
 * @see com.animal.api.shelter.model.response.ShelterBoardListResponseDTO
 * @see com.animal.api.shelter.model.response.ShelterDetailResponseDTO
 * @see com.animal.api.shelter.model.response.ShelterVolunteerReviewResponseDTO
 * @see com.animal.api.shelter.model.response.ShelterVolunteersResponseDTO
 * @see com.animal.api.animal.model.response.AnimalDetailResponseDTO
 */
@RestController
@RequestMapping("/api/shelters")
public class UserShelterController {

	@Autowired
	private UserShelterService userShelterService;
	@Autowired
	private UserAnimalService userAnimalService;
	@Autowired
	private UserVolunteersService userVolunteerService;

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
		List<AllShelterListResponseDTO> shelterList = null;
		PageInformationDTO pageInfo = null;
		if (shelterName != null || shelterAddress != null || shelterType != null) {
			shelterList = userShelterService.searchShelters(cp, shelterName, shelterAddress, shelterType);
			pageInfo = userShelterService.searchSheltersPageInfo(cp, shelterName, shelterAddress, shelterType);
		} else {
			shelterList = userShelterService.getAllShelters(cp);
			pageInfo = userShelterService.getAllSheltersPageInfo(cp);
		}

		if (shelterList == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "잘못된 접근입니다."));
		} else if (shelterList.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "조회된 데이터가 없습니다."));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkPageResponseDTO<List<AllShelterListResponseDTO>>(200, "조회성공", shelterList, pageInfo));
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
		ShelterDetailResponseDTO dto = userShelterService.getShelterDetail(idx);
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
		List<ShelterVolunteersResponseDTO> volunteerList = userShelterService.getShelterVolunteers(cp, idx);
		PageInformationDTO pageInfo = userShelterService.getShelterVolunteersPageInfo(cp, idx);

		if (volunteerList == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "잘못된 접근입니다."));
		} else if (volunteerList.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "조회된 데이터가 없습니다."));
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(
					new OkPageResponseDTO<List<ShelterVolunteersResponseDTO>>(200, "조회 성공", volunteerList, pageInfo));
		}
	}

	/**
	 * 보호시설의 상세정보에서 봉사의 상세 정보를 조회하는 메서드
	 * 
	 * @param shelterIdx   보호시설의 idx
	 * @param volunteeIidx 봉사 번호
	 * @return 봉사 상세 정보
	 */

	@GetMapping("/{shelterIdx}/volunteers/{volunteerIdx}")
	public ResponseEntity<?> getVolunteersDetail(@PathVariable int shelterIdx, @PathVariable int volunteerIdx) {
		AllVolunteersResponseDTO dto = userVolunteerService.getVolunteersDetail(volunteerIdx);

		if (dto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "해당 봉사가 존재하지 않습니다."));
		} else if (dto.getUserIdx() != shelterIdx) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "해당 보호시설의 봉사가 아닙니다."));
		} else {
			return ResponseEntity.ok(new OkResponseDTO<AllVolunteersResponseDTO>(200, "봉사 상세정보 조회 성공", dto));
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

		List<ShelterAnimalsResponseDTO> animalList = null;
		PageInformationDTO pageInfo = null;

		if (type != null || breed != null || gender != null || neuter != 0 || age != 0 || adoptionStatus != null
				|| personality != null || size != 0 || name != null) {
			animalList = userShelterService.searchShelterAnimals(idx, cp, type, breed, gender, neuter, age,
					adoptionStatus, personality, size, name);
			pageInfo = userShelterService.searchShelterAnimalsPageInfo(idx, cp, type, breed, gender, neuter, age,
					adoptionStatus, personality, size, name);
		} else {
			animalList = userShelterService.getAllShelterAnimals(cp, idx);
			pageInfo = userShelterService.getAllShelterAnimalsPageInfo(cp, idx);
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
	 * 보호시설 페이지에서 유기동물의 상세 정보를 조회하는 메서드
	 * 
	 * @param shelterIdx 보호시설의 idx
	 * @param animalIdx  유기동물 관리번호
	 * @return 유기동물 정보
	 */
	@GetMapping("/{shelterIdx}/animals/{animalIdx}")
	public ResponseEntity<?> getAnimalDetail(@PathVariable int shelterIdx, @PathVariable int animalIdx) {
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
	 * 보호시설의 상세 정보에서 보호시설이 남긴 게시글 조회
	 * 
	 * @param idx 보호시설의 idx
	 * @param cp  현재 페이지
	 * @return 해당 보호시설의 게시물 리스트
	 */
	@GetMapping("/{idx}/boards")
	public ResponseEntity<?> getShelterBoards(@PathVariable int idx,
			@RequestParam(value = "cp", defaultValue = "0") int cp) {

		List<ShelterBoardListResponseDTO> boardList = userShelterService.getShelterBoards(cp, idx);
		PageInformationDTO pageInfo = userShelterService.getShelterBoardsPageInfo(cp, idx);

		if (boardList == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "잘못된 접근입니다."));
		} else if (boardList.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "조회된 데이터가 없습니다."));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkPageResponseDTO<List<ShelterBoardListResponseDTO>>(200, "조회 성공", boardList, pageInfo));
		}
	}

	/**
	 * 보호시설의 게시글 상세 조회 메서드
	 * 
	 * @param userIdx  보호시설 idx
	 * @param boardIdx 게시글의 idx
	 * @return 해당 게시글
	 */
	@GetMapping("/{userIdx}/boards/{boardIdx}")
	public ResponseEntity<?> getShelterBoardDetail(@PathVariable int userIdx, @PathVariable int boardIdx) {
		ShelterBoardDetailResponseDTO dto = userShelterService.getShelterBoardDetail(boardIdx);

		if (dto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "해당 글이 존재하지 않습니다."));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new OkResponseDTO<ShelterBoardDetailResponseDTO>(200, "게시글 조회 성공", dto));
		}

	}

	/**
	 * 해당 보호시설의 봉사에 남겨진 리뷰 조회 메서드
	 * 
	 * @param idx 보소시설 idx
	 * @param cp  현재 페이지
	 * @return 봉사 리뷰 리스트
	 */
	@GetMapping("/{idx}/reviews/voluntees")
	public ResponseEntity<?> getShelterVolunteerReviews(@PathVariable int idx,
			@RequestParam(value = "cp", defaultValue = "0") int cp) {
		List<ShelterVolunteerReviewResponseDTO> reviewList = userShelterService.getShelterVolunteerReviews(cp, idx);
		PageInformationDTO pageInfo = userShelterService.getShelterVolunteerReviewsPageInfo(cp, idx);

		if (reviewList == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "잘못된 접근입니다."));
		} else if (reviewList.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "조회된 데이터가 없습니다."));
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(
					new OkPageResponseDTO<List<ShelterVolunteerReviewResponseDTO>>(200, "조회 성공", reviewList, pageInfo));
		}
	}

	/**
	 * 해당 보호시설의 입양에 남겨진 리뷰 조회 메서드
	 * 
	 * @param idx 보소시설 idx
	 * @param cp  현재 페이지
	 * @return 입양 리뷰 리스트
	 */
	@GetMapping("/{idx}/reviews/adoptions")
	public ResponseEntity<?> getShelterAdoptionReviews(@PathVariable int idx,
			@RequestParam(value = "cp", defaultValue = "0") int cp) {
		List<ShelterAdoptionReviewResponseDTO> reviewList = userShelterService.getShelterAdoptionReviews(cp, idx);
		PageInformationDTO pageInfo = userShelterService.getShelterAdoptionReviewsPageInfo(cp, idx);

		if (reviewList == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400, "잘못된 접근입니다."));
		} else if (reviewList.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404, "조회된 데이터가 없습니다."));
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(
					new OkPageResponseDTO<List<ShelterAdoptionReviewResponseDTO>>(200, "조회 성공", reviewList, pageInfo));
		}
	}

}
