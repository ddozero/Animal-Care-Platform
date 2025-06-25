package com.animal.api.mypage.adoption.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdoptionListResponseDTO {
    
    private int adoptionConsultIdx; // 입양 상담 고유 번호
    private String animalName;      // 유기동물 이름
    private String statusText;      // 입양 상담 상태 텍스트
    private int animalIdx;          // 유기동물 고유 번호 (상세 이동용)
    private String imagePath;       // 유기동물 대표 이미지 경로 (FileManager 이용해서 서비스에서 주입)
}
