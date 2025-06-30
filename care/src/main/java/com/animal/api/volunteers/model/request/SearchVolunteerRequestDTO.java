package com.animal.api.volunteers.model.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchVolunteerRequestDTO {
	
	private int cp;
	private int listSize;
	private String title;
	private String content;
	private String location;
	private String status;
	private String shelter;
	private String shelterType;
	private LocalDate volunteerDate;
	private String type;
	private int time;
	

}
