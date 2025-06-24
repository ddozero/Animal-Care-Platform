package com.animal.api.mypage.volunteers.model.response;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VolunteerDetailResponseDTO {

    private int volunteerRequestIdx; // 신청 고유 번호

    private String title;             // 봉사 제목
    private String content;           // 봉사 본문
    private String shelterName;       // 보호소 이름
    private String location;          // 봉사 장소
    private String statusText;        // 신청 상태 텍스트

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date volunteerDate;       // 봉사 일시

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date createdAt;           // 봉사글 등록일

    private int capacity;             // 모집인원
    private int applicants;           // 신청인원
    private String contact;           // 신청인 연락처 

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date participatedAt;      // 내가 신청한 날짜

    private List<String> imagePaths;  // 첨부 이미지 경로들 (FileManager 사용)
    
}
