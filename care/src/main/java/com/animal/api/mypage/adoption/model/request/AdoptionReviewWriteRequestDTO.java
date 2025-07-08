package com.animal.api.mypage.adoption.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdoptionReviewWriteRequestDTO {
    private int adoptionConsultIdx;  // 어떤 입양 신청에 대한 후기인지
    private String content;          // 후기 본문
}