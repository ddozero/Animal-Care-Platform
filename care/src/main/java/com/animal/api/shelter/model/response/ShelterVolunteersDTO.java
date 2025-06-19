package com.animal.api.shelter.model.response;

import java.sql.Timestamp;

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
	private Timestamp createdAT;
}
