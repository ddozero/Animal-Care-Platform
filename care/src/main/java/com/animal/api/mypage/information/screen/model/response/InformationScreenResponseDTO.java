package com.animal.api.mypage.information.screen.model.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InformationScreenResponseDTO {
	
    private String username;               // 사용자 이름
    private int point;                     // 보유 포인트
    private int volunteerCount;            // 봉사 횟수
    private int adoptionCount;             // 입양 횟수
    private int totalDonationAmount;       // 총 기부 금액
    private List<RecentActivityDTO> activityHistory;  // 최근 활동 내역 (문자 리스트)
    
}
