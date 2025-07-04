package com.animal.api.management.shelter.model.response;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManageAdoptionReviewResponseDTO {
	
	private int reviewIdx;
	private int userIdx;
	private int shelterIdx;
	private int animalIdx;
	private String nickName;
	private String shelterName;
	private String animalName;
	private String content;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Timestamp createdAt;
	private int ref;
	private int lev;
	private int turn;
	private String imagePath;

}
