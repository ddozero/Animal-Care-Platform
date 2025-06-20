package com.animal.api.volunteers.model.response;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchVolunteerResponseDTO {
	
	private int cp;
	private int listSize;
	private String title;
	private String content;
	private String location;
	private String status;
	private String shelter;
	private String shelterType;
	private Timestamp volunteerDate;
	private String type;
	private int time;
	

}
