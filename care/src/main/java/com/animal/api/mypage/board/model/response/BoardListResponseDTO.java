package com.animal.api.mypage.board.model.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardListResponseDTO {

    private int boardIdx;       // 게시글 고유 번호
    private int boardType;      // 게시판 종류 번호
    private String boardTypeName; // 게시판 종류 이름 (예: 자유게시판)
    private String title;       // 게시글 제목
    private String nickname;    // 작성자 닉네임
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;     // 작성일
    private int views;          // 조회수
	
}
