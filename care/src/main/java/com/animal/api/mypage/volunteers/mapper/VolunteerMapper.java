package com.animal.api.mypage.volunteers.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.animal.api.mypage.volunteers.model.entity.VolunteerReviewEntity;
import com.animal.api.mypage.volunteers.model.response.VolunteerDetailResponseDTO;
import com.animal.api.mypage.volunteers.model.response.VolunteerListResponseDTO;

@Mapper
public interface VolunteerMapper {

	//봉사내역 리스트
	List<VolunteerListResponseDTO> findVolunteerListByUserIdx (int userIdx);
	
	//봉사 상세 내역
	VolunteerDetailResponseDTO findVolunteerDetailByRequestIdx(@Param("volunteerRequestIdx") int volunteerRequestIdx);
	
    // 1. 참여완료 상태인지 확인
    boolean isCompletedVolunteerRequest(@Param("userIdx") int userIdx, @Param("volunteerRequestIdx") int volunteerRequestIdx);

    // 2. 이미 후기 작성했는지 확인
    boolean hasAlreadyWrittenReview(@Param("userIdx") int userIdx, @Param("volunteerRequestIdx") int volunteerRequestIdx);

    // 3. VOLUNTEER_IDX 가져오기
    int findVolunteerIdxByRequestIdx(int volunteerRequestIdx);

    // 4. 후기 등록 (REF = IDX)
    void insertVolunteerReview(VolunteerReviewEntity review);

    // 5. 포인트 지급
    void grantVolunteerReviewPoint(int userIdx);
    
    // 6. 사용자 포인트 증가
    void increaseUserPoint(int userIdx);
}
