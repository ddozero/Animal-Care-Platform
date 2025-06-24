package com.animal.api.mypage.volunteers.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerReviewWriteRequestDTO {

    private int volunteerRequestIdx;  // 신청 기록 IDX
    private String content; // 후기 본문
}
