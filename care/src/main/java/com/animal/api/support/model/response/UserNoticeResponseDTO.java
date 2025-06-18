package com.animal.api.support.model.response;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserNoticeResponseDTO {

	private int idx;
	private int user_idx;
	private int board_type_idx;
	private String title;
	private String content;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Timestamp created_at;
	private int views;
	private int ref;
	private int lev;
	private int turn;

}
