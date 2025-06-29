package com.animal.api.management.volunteers.model.response;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShelterVolunteerDetailResponseDTO {
	private int idx;
	private int userIdx;
	private int volunteerTypeIdx;
	private String volunteerTypeName;
	private int volunteerStatusIdx;
	private String volunteerStatusName;
	private String title;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Timestamp volunteerDate;
	private int time;
	private String location;
	private int capacity;
	private int applicants;
	private String content;
	private String ageTarget;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Timestamp createdAt;
	private String imagePath;
}
