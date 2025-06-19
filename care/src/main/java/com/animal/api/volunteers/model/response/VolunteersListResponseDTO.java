package com.animal.api.volunteers.model.response;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VolunteersListResponseDTO {

	private int idx;
	private String title;
	private Date volunteerDate;
	private int time;
	private String location;
	private int capacity;
	private int applicants;
	private String content;
	private String ageTarget;
	private Date createdAt;
	private String type;
	private String status;
	private String sheelter;

}
