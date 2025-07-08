package com.animal.api.mypage.volunteers.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VolunteerListResponseDTO {

    private Integer volunteerRequestIdx; // 봉사 신청 고유 번호 (VOLUNTEER_REQUESTS.IDX)
    private String title;             // 봉사글 제목 (VOLUNTEERS.TITLE)
    private String shelterName;       // 보호소 이름 (SHELTERS.SHELTER_NAME)
    private String location;          // 봉사 장소 (VOLUNTEERS.LOCATION)
    private String statusText;        // 신청 상태 (신청완료/승인완료/거절됨/참여완료)
    private String participatedAt;    // 신청일 (VOLUNTEER_REQUESTS.CREATED_AT)
    private String imagePath;         // 썸네일 이미지 경로 (별도 조합해서 넣을 예정)
    
}
