package com.animal.api.mypage.adoption.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.animal.api.mypage.adoption.model.entity.AdoptionReviewEntity;
import com.animal.api.mypage.adoption.model.response.AdoptionDetailResponseDTO;
import com.animal.api.mypage.adoption.model.response.AdoptionListResponseDTO;

@Mapper
public interface AdoptionMapper {
    
	// 1. 입양 내역 리스트 조회
    List<AdoptionListResponseDTO> findAdoptionListByUserIdx(@Param("userIdx") int userIdx);
    
    // 2. 입양 상세 정보 조회 (입양 신청 고유번호 기준)
    AdoptionDetailResponseDTO findAdoptionDetailByConsultIdx(@Param("adoptionConsultIdx") int adoptionConsultIdx);
    
    // 3. 상담 완료 상태인지 확인
    boolean isCompletedAdoptionConsult(@Param("adoptionConsultIdx") int consultIdx, @Param("userIdx") int userIdx);

    // 4. 이미 후기 작성했는지 여부
    boolean hasAlreadyWrittenAdoptionReview(@Param("adoptionConsultIdx") int consultIdx, @Param("userIdx") int userIdx);

    // 5. 입양 신청 기준 유기동물 번호 조회
    int findAnimalIdxByConsultIdx(@Param("adoptionConsultIdx") int consultIdx);
    
    // 6. 후기 등록 (엔티티 기반)
    void insertAdoptionReview(AdoptionReviewEntity review);
    
    // 7. REF 컬럼 자기 자신의 IDX로 업데이트
    void updateAdoptionReviewRef(@Param("idx") int idx);

    // 8. 입양 후기 포인트 지급 (POINT_GRANTS insert)
    void grantAdoptionReviewPoint(@Param("userIdx") int userIdx);

    // 9. 사용자 포인트 증가 (USERS 테이블)
    void increaseUserPoint(int userIdx);
}