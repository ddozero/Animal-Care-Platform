package com.animal.api.mypage.volunteers.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VolunteerReviewEntity {
    private int idx; // keyProperty로 REF에 사용됨
    private int userIdx;
    private int volunteerIdx;
    private String content;
}