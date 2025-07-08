package com.animal.api.mypage.adoption.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.animal.api.mypage.adoption.model.request.AdoptionReviewWriteRequestDTO;
import com.animal.api.mypage.adoption.model.response.AdoptionDetailResponseDTO;
import com.animal.api.mypage.adoption.model.response.AdoptionListResponseDTO;

public interface AdoptionService {

    // 입양 내역 리스트 조회
    List<AdoptionListResponseDTO> getAdoptionListByUserIdx(int userIdx);

    // 입양 상세 조회
    AdoptionDetailResponseDTO getAdoptionDetailByConsultIdx(int adoptionConsultIdx);

    // 입양 후기 작성
    void writeAdoptionReview(int userIdx, AdoptionReviewWriteRequestDTO dto, MultipartFile image);
}

