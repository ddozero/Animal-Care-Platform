package com.animal.api.shelter.model.response;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShelterVolunteersDTO {
	private int idx;
	private String title;
	private String shelterName;
	private String location;
	private String volunteerStatus;
	private int volunteerTime;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Timestamp createdAT;
}
