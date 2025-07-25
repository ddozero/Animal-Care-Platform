package com.animal.api.board.model.response;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDetailResponseDTO {
	private int idx;
	private int userIdx;
	private String nickname;
	private String title;
	private String content;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Timestamp createdAt;
	private int views;
	private int likeCount;
	private List<String> filePaths;
	// 로그인후 상세페이지 조회시 좋아요 클릭 여부(true:클릭/false:클릭x)
	private boolean heart;
}
