package com.animal.api.mypage.volunteers.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.animal.api.mypage.volunteers.model.request.VolunteerReviewWriteRequestDTO;
import com.animal.api.mypage.volunteers.model.response.VolunteerDetailResponseDTO;
import com.animal.api.mypage.volunteers.model.response.VolunteerListResponseDTO;

public interface VolunteerService {

	// 봉사 신청 내역 리스트 조회
    List<VolunteerListResponseDTO> getVolunteerListByUserIdx(int userIdx);
    
    // 봉사 상세 내역
    VolunteerDetailResponseDTO getVolunteerDetailByRequestIdx(int volunteerRequestIdx, int userIdx);
    
    // 후기 작성
    boolean writeVolunteerReview(int userIdx, VolunteerReviewWriteRequestDTO dto, MultipartFile image);
}
