package com.animal.api.mypage.adoption.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdoptionReviewEntity {
    private int idx;
    private int userIdx;
    private int animalIdx;
    private String content;
    private int ref;
    private int lev;
    private int turn;
}
