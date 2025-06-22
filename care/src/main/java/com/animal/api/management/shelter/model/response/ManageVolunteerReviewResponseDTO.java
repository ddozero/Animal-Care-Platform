package com.animal.api.management.shelter.model.response;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManageVolunteerReviewResponseDTO {

	private int reviewIdx;
	private int userIdx;
	private int shelterIdx;
	private int volunteerIdx;
	private String nickName;
	private String volunteerTitle;
	private String content;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Timestamp createdAt;
	private int ref;
	private int lev;
	private int turn;

}
