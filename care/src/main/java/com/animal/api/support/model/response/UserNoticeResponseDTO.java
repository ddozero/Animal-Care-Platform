package com.animal.api.support.model.response;

import java.sql.Date;

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
	private Date created_at;
	private int views;
	private int ref;
	private int lev;
	private int turn;

}
