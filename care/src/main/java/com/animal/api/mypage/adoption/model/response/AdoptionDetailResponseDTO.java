package com.animal.api.mypage.adoption.model.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdoptionDetailResponseDTO {
    private String animalName; 		 // 유기동물 이름
    private String statusText;  	 // 입양 신청 상태 텍스트
    private int breedIdx;            // 품종 번호
    private String breed; 			 // 품종 이름
    private String gender; 			 // 성별
    private int age; 				 // 나이
    private int size;			 	 // 크기(체중)
    private boolean neuter; 		 // 중성화 여부
    private String personality;		 // 성격 이름
    private String shelterName; 	 // 보호소 이름
    private String description; 	 // 동물 소개 내용
    private List<String> imagePaths; // 첨부된 유기동물 이미지 경로 목록
}