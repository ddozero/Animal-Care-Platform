package com.animal.api.management.shelter.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.animal.api.common.model.PageInformationDTO;
import com.animal.api.common.util.FileManager;
import com.animal.api.management.shelter.mapper.ManagementShelterMapper;
import com.animal.api.management.shelter.model.request.ManageAdoptionReplyRequestDTO;
import com.animal.api.management.shelter.model.request.ManageVolunteerReplyRequestDTO;
import com.animal.api.management.shelter.model.request.ShelterBoardRequestDTO;
import com.animal.api.management.shelter.model.request.ShelterInfoUpdateRequestDTO;
import com.animal.api.management.shelter.model.response.AllManageShelterResponseDTO;
import com.animal.api.management.shelter.model.response.ManageAdoptionReviewResponseDTO;
import com.animal.api.management.shelter.model.response.ManageVolunteerReviewResponseDTO;
import com.animal.api.management.shelter.model.response.ShelterBoardResponseDTO;

@Service
@Primary
public class ShelterManageServiceImple implements ShelterManageService {

	@Autowired
	private ManagementShelterMapper mapper;

	@Autowired
	private FileManager fileManager;
	
	private int listSize = 10;
	private int pageSize = 5;

	@Override
	public AllManageShelterResponseDTO getShelterInfo(int idx) {
		AllManageShelterResponseDTO dto = mapper.getShelterInfo(idx);
		if (dto != null && dto.getDescription() != null) {
			dto.setImagePaths(fileManager.getImagePath("shelters", idx));
			dto.setFilePaths(fileManager.getFilePath("shelters", idx)); // 사업자등록증 조회
		}
		return dto;
	}

	@Override
	public int updateShelterInfo(ShelterInfoUpdateRequestDTO dto, int userIdx) {
		int count = mapper.updateSheterInfo(dto);
		return count;
	}

	@Override
	public int uploadShelterFile(MultipartFile[] files, int idx) { // 보호시설 info 수정 파일업로드
		boolean result = fileManager.uploadImages("shelters", idx, files);

		if (result) {
			return UPLOAD_OK;
		} else {
			return ERROR;
		}
	}

	//// 보호시설 리뷰
	@Override
	public List<ManageVolunteerReviewResponseDTO> getVolunteerReview(int cp, int idx) {
		
		if (cp == 0) {
			cp = 1;
		} 
		cp = (cp - 1) * listSize;
		
		
		Map<String, Integer> map = new HashMap<String, Integer>();

		map.put("listSize", listSize);
		map.put("cp", cp);
		map.put("idx", idx);


		List<ManageVolunteerReviewResponseDTO> reviewLists = mapper.getVolunteerReview(map);

		if (reviewLists != null) {
			for (ManageVolunteerReviewResponseDTO dto : reviewLists) { // 이미지 경로 가져오기
				List<String> imagePaths = fileManager.getImagePath("volunteerReviews", dto.getReviewIdx());
				if (imagePaths != null || imagePaths.size() != 0) {
					dto.setImagePath(imagePaths.get(0));
				}
			}
		}
		return reviewLists;
	}
	
	@Override
	public PageInformationDTO getVolunteerReviewPage(int cp, int idx) {
		if (cp == 0) {
			cp = 1;
		}
		int totalCnt = mapper.getVolunteerReviewTotalCnt(idx);
		PageInformationDTO page = new PageInformationDTO(totalCnt, listSize, pageSize, cp);
		return page;
	}

	@Override
	public List<ManageAdoptionReviewResponseDTO> getAdoptionReview(int cp, int idx) {
		
		if (cp == 0) {
			cp = 1;
		} 
		cp = (cp - 1) * listSize;
		
		Map<String, Integer> map = new HashMap<String, Integer>();

		map.put("listSize", listSize);
		map.put("cp", cp);
		map.put("idx", idx);

		List<ManageAdoptionReviewResponseDTO> reviewLists = mapper.getAdoptionReview(map);

		if (reviewLists != null) {
			for (ManageAdoptionReviewResponseDTO dto : reviewLists) { // 이미지 경로 가져오기
				List<String> imagePaths = fileManager.getImagePath("adoptionReviews", dto.getReviewIdx());
				if (imagePaths != null || imagePaths.size() != 0) {
					dto.setImagePath(imagePaths.get(0));
				}
			}
		}
		return reviewLists;
	}
	
	@Override
	public PageInformationDTO getAdoptionReviewPage(int cp, int idx) {
		if (cp == 0) {
			cp = 1;
		}
		int totalCnt = mapper.getAdoptionReviewTotalCnt(idx);
		PageInformationDTO page = new PageInformationDTO(totalCnt, listSize, pageSize, cp);
		return page;
	}
	
	//봉사 리뷰 답글
	@Override
	@Transactional
	public int addVolunteerReviewApply(ManageVolunteerReplyRequestDTO dto, int userIdx, int reviewIdx) {

	    ManageVolunteerReviewResponseDTO reviewIdxVR = mapper.getReviewIdxVR(dto.getRef());
	    if (reviewIdxVR == null) {
	        return NOT_REVIEW;  // 부모 리뷰 조회 실패시
	    }
	    
	    if (reviewIdxVR.getLev() != 0) {
	        return NOT_ALLOWED_REPLY;  // 리뷰 답글 1번만 가능하게 설정
	    }

	    int maxTurn = mapper.getMaxTurnVR(reviewIdxVR.getRef());

	    dto.setRef(reviewIdxVR.getRef());                    
	    dto.setReviewIdx(reviewIdxVR.getReviewIdx());        
	    dto.setLev(reviewIdxVR.getLev() + 1);
	    dto.setTurn(maxTurn + 1);
	    dto.setUserIdx(userIdx); 

	    Integer shelterCheck = mapper.checkShelterUserVR(dto);
	    if (shelterCheck == null || shelterCheck == 0) {
	        return NOT_SHELTER_MANAGER;
	    }

	    int result = mapper.addVolunteerReviewApply(dto);

	    return (result > 0) ? UPDATE_OK : ERROR;
	}

	
	@Override
	public int updateVolunteerReviewApply(ManageVolunteerReplyRequestDTO dto, int userIdx, int reviewIdx) {

		Integer reviewCheck = mapper.checkVolunteerReview(reviewIdx);

		if (reviewCheck == null) { // 리뷰글이 있는지 확인
			return NOT_REVIEW;
		}

		dto.setReviewIdx(reviewIdx);
		dto.setUserIdx(userIdx);

		Integer shelterCheck = mapper.checkShelterUserVR(dto);
		if (shelterCheck == null || shelterCheck == 0) { // 해당 보호소 관리자인지 확인
			return NOT_SHELTER_MANAGER;
		}

		int count = mapper.updateVolunteerReviewApply(dto);
		if (count > 0) {
			return UPDATE_OK;
		} else {
			return ERROR;
		}
	}

	@Override
	public int deleteVolunteerReviewApply(int userIdx, int reviewIdx) {

		Integer reviewCheck = mapper.checkVolunteerReview(reviewIdx);
		if (reviewCheck == null) {// 리뷰글이 있는지 확인
			return NOT_REVIEW;
		}

		ManageVolunteerReplyRequestDTO dto = new ManageVolunteerReplyRequestDTO();
		dto.setReviewIdx(reviewIdx);
		dto.setUserIdx(userIdx);

		Integer shelterCheck = mapper.checkShelterUserVR(dto);
		if (shelterCheck == null || shelterCheck == 0) {// 해당 보호소 관리자인지 확인
			return NOT_SHELTER_MANAGER;
		}

		int count = mapper.deleteVolunteerReviewApply(dto);
		if (count > 0) {
			return DELETE_OK;
		} else {
			return ERROR;
		}
	}
	
	//입양 리뷰 답글	
	@Override
	@Transactional
	public int addAdoptionReviewApply(ManageAdoptionReplyRequestDTO dto, int userIdx, int reviewIdx) {

		ManageAdoptionReviewResponseDTO reviewIdxAR = mapper.getReviewIdxAR(dto.getRef());
	    if (reviewIdxAR == null) {
	        return NOT_REVIEW;  // 부모 리뷰 조회 실패시
	    }
	    
	    if (reviewIdxAR.getLev() != 0) {
	        return NOT_ALLOWED_REPLY;  // 리뷰 답글 1번만 가능하게 설정
	    }

	    int maxTurn = mapper.getMaxTurnVR(reviewIdxAR.getRef());

	    dto.setRef(reviewIdxAR.getRef());                    
	    dto.setReviewIdx(reviewIdxAR.getReviewIdx());        
	    dto.setLev(reviewIdxAR.getLev() + 1);
	    dto.setTurn(maxTurn + 1);
	    dto.setUserIdx(userIdx); 

	    Integer shelterCheck = mapper.checkShelterUserAR(dto);
	    if (shelterCheck == null || shelterCheck == 0) {
	        return NOT_SHELTER_MANAGER;
	    }

	    int result = mapper.addAdoptionReviewApply(dto);

	    return (result > 0) ? UPDATE_OK : ERROR;
	}
	
	

	@Override
	public int updateAdoptionReviewApply(ManageAdoptionReplyRequestDTO dto, int userIdx, int reviewIdx) {

		Integer reviewCheck = mapper.checkAdoptionReview(reviewIdx);

		if (reviewCheck == null) { // 리뷰글이 있는지 확인
			return NOT_REVIEW;
		}

		dto.setReviewIdx(reviewIdx);
		dto.setUserIdx(userIdx);

		Integer shelterCheck = mapper.checkShelterUserAR(dto);
		if (shelterCheck == null || shelterCheck == 0) { // 해당 보호소 관리자인지 확인
			return NOT_SHELTER_MANAGER;
		}

		int count = mapper.updateAdoptionReviewApply(dto);
		if (count > 0) {
			return UPDATE_OK;
		} else {
			return ERROR;
		}
	}

	@Override
	public int deleteAdoptionReviewApply(int userIdx, int reviewIdx) {

		Integer reviewCheck = mapper.checkAdoptionReview(reviewIdx);
		if (reviewCheck == null) {// 리뷰글이 있는지 확인
			return NOT_REVIEW;
		}

		ManageAdoptionReplyRequestDTO dto = new ManageAdoptionReplyRequestDTO();
		dto.setReviewIdx(reviewIdx);
		dto.setUserIdx(userIdx);

		Integer shelterCheck = mapper.checkShelterUserAR(dto);
		if (shelterCheck == null || shelterCheck == 0) {// 해당 보호소 관리자인지 확인
			return NOT_SHELTER_MANAGER;
		}

		int count = mapper.deleteAdoptionReviewApply(dto);
		if (count > 0) {
			return DELETE_OK;
		} else {
			return ERROR;
		}
	}

	//// 보호시설 게시판
	@Override
	public List<ShelterBoardResponseDTO> getShelterBoardList(int userIdx, int cp) {
		
		if (cp == 0) {
			cp = 1;
		} 
		cp = (cp - 1) * listSize;

		Map<String, Integer> map = new HashMap<String, Integer>();

		map.put("userIdx", userIdx);
		map.put("listSize", listSize);
		map.put("cp", cp);

		List<ShelterBoardResponseDTO> boardLists = mapper.getShelterBoardList(map);

		return boardLists;
	}
	
	@Override
	public PageInformationDTO getShelterBoardTotalCnt(int userIdx, int cp) {
		
		if (cp == 0) {
			cp = 1;
		}
		
		int totalCnt = mapper.getShelterBoardTotalCnt(userIdx);
		
		PageInformationDTO page = new PageInformationDTO(totalCnt, listSize, pageSize, cp);
		return page;
	}

	@Override
	public ShelterBoardResponseDTO getShelterBoardDetail(int idx, int userIdx) {

		ShelterBoardResponseDTO dto = mapper.getShelterBoardDetail(idx, userIdx);

		if (dto == null) {
			return null;
		}

		mapper.updateBoardViews(idx);
		List<String> filePath = fileManager.getFilePath("boards", idx);
		if (filePath != null && !filePath.isEmpty()) {
		    dto.setFilesPath(filePath.get(0)); // 첫 파일만
		}

		return dto;
	}

	@Override
	public int addShelterBoard(ShelterBoardRequestDTO dto, int userIdx) {

		int ref = mapper.getMaxRef();
		dto.setRef(ref + 1);

		int result = mapper.addShelterBoard(dto, userIdx);

		if (result == 1) {
			return WRITE_OK;
		} else {
			return ERROR;
		}
	}

	@Override
	public int uploadBoardFile(MultipartFile[] files, int idx) { // 보호시설 게시판 파일 업로드

		boolean result = fileManager.uploadFiles("boards", idx, files);

		 if (result) {
		        return UPLOAD_OK;
		    } else {
		        // 업로드 실패 시, 등록한 게시글을 바로 삭제
		    	ShelterBoardRequestDTO dto = new ShelterBoardRequestDTO();
		        dto.setIdx(idx);
		        mapper.deleteShelterBoard(dto);
		        return ERROR;
		    }
	}

	@Override
	public int updateShelterBoard(ShelterBoardRequestDTO dto) {

		int boardCheck = mapper.checkShelterBoard(dto.getIdx());
		if (boardCheck == 0) {
			return NOT_EXIST_BOARD;
		}

		Integer userCheck = mapper.checkWriter(dto);
		if (userCheck == null || userCheck == 0) {
			return NOT_SHELTER_MANAGER;
		}

		int result = mapper.updateShelterBoard(dto);
		if (result == 1) {
			return UPDATE_OK;
		} else {
			return ERROR;
		}
	}

	@Override
	public int deleteShelterBoard(ShelterBoardRequestDTO dto, int idx) {

		int boardCheck = mapper.checkShelterBoard(dto.getIdx());
		if (boardCheck == 0) {
			return NOT_EXIST_BOARD;
		}

		Integer userCheck = mapper.checkWriter(dto);
		if (userCheck == null || userCheck == 0) {
			return NOT_SHELTER_MANAGER;
		}

		int result = mapper.deleteShelterBoard(dto);
		if (result == 1) {
			fileManager.deleteFolder("boards", idx);
			return DELETE_OK;
		} else {
			return ERROR;
		}
	}

}
