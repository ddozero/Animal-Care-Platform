package com.animal.api.mypage.board.model.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardPageResponseDTO {
	
    private int totalCount; // 전체 글 개수
    private List<BoardListResponseDTO> list; // 게시글 목록
    
}
