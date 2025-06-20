package com.animal.api.volunteers.model.response;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VolunteersSubmitResponseDTO {
	
	private int idx;
	private int volunteerIdx;
	private int userIdx;
	private int statusIdx;
	private String type;
	private String name;
	private String email;
	private String tel;
	private Timestamp birthDate;
	private String groupName;
	private String groupTel;
	private int male;
	private int female;
	private String description;
	
}
