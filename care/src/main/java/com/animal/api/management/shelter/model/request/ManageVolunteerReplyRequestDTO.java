package com.animal.api.management.shelter.model.request;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManageVolunteerReplyRequestDTO {

	private int reviewIdx;
	private int userIdx;
	private int volunteerIdx;
	private String content;
	private int ref;
	private int lev;
	private int turn;
	private Timestamp createAt;

}
