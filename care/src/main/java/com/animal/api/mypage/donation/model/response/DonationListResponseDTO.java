package com.animal.api.mypage.donation.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonationListResponseDTO {
	
    private int donationIdx;               // 기부 게시글 고유 번호
    private String donationName;          // 기부 사업명
    private String donationStatusText;    // 기부 상태 텍스트 (대기, 진행중 등)
    private String startDate;             // 시작일 (yyyy-MM-dd)
    private String endDate;               // 마감일 (yyyy-MM-dd)
    private int amount;                   // 목표 모금액
    private int completionAmount;         // 누적 모금액
    private int completionRate;           // 달성률 (퍼센트 단위)
    private String sponsor;               // 모금 단체명
    private String createdAt;             // 등록일 (yyyy-MM-dd HH:mm:ss)
    private String imagePath;             // 대표 이미지 경로 (FileManager에서 지정할 예정)
}
