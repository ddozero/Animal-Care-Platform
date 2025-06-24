package com.animal.api.mypage.information.screen.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.animal.api.mypage.information.screen.model.response.RecentActivityDTO;

@Mapper
public interface InformationScreenMapper {

    // 봉사 완료 횟수
    int countVolunteer(@Param("userIdx") int userIdx);

    // 입양 후기 작성 건수 (입양 완료 기준)
    int countAdoption(@Param("userIdx") int userIdx);

    // 총 기부 금액
    int sumDonations(@Param("userIdx") int userIdx);

    // 최근 활동 내역 (문자 리스트)
    List<RecentActivityDTO> selectRecentActivities(@Param("userIdx") int userIdx);
}
