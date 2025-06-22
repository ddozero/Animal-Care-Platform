package com.animal.api.management.shelter.model.response;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManageAdoptionReviewResponseDTO {

	private int idx;
	private int userIdx;
	private String nickName;
	private String animalName;
	private String content;
	private Timestamp createdAt;
	private int ref;
	private int lev;
	private int turn;

}
